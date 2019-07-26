const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

// use mainly for listening to events and real-time data changes

// admin reference
const admin = require("firebase-admin");
admin.initializeApp(functions.config().firebase);

 /**
  * @see use SendGrid to send emails from cloud functions, best practice (https://firebase.google.com/docs/functions/tips)
  */

// **********************************
// **********************************

// send confirmation email and receipt of appointment booking to player

// send out confirmation email of refund for cancelled session

// re-adjust each coaches calender in firestore, once the day is over

// send out email to all available players of spot openings for certain coaches

// send out rating review to all players who attended a group session

// **********************************

// create new Stripe Customer when a user is authenticated
exports.createStripeCustomer = functions.auth.user().onCreate(async (user) => {
  const customer = await stripe.customers.create({email: user.email});
  return admin.firestore().collection(‘players’)
      .doc(user.uid).set({customer_id: customer.id});
});

exports.addPaymentSource = functions.firestore
    .document(‘/stripe_customers/{userId}/tokens/{pushId}’)
    .onWrite(async (change, context) => {
    const source = change.after.data();
    const token = source.token;
    if (source === null) {
        return null;
    }
    try {
    const snapshot = await
    admin.firestore()
    .collection(‘stripe_customers’)
    .doc(context.params.userId)
    .get();
    const customer = snapshot.data().customer_id;
    const response = await stripe.customers
        .createSource(customer, {source: token});
    return admin.firestore()
    .collection(‘stripe_customers’)
    .doc(context.params.userId)
    .collection(“sources”)
    .doc(response.fingerprint)
    .set(response, {merge: true});
    } catch (error) {
    await change.after.ref
        .set({‘error’:userFacingMessage(error)},{merge:true});
    }
});

// necessary for idempotency
exports.createStripeCharge = functions.firestore
    .document(‘stripe_customers/{userId}/charges/{id}’)
    .onCreate(async (snap, context) => {
    const val = snap.data();
    try {
    // Look up the Stripe customer id written in createStripeCustomer
    const snapshot = await admin.firestore()
    .collection(`stripe_customers`)
    .doc(context.params.userId).get();
    
    const snapval = snapshot.data();
    const customer = snapval.customer_id;
    // Create a charge using the pushId as the idempotency key
    // protecting against double charges
    const amount = val.amount;
    const idempotencyKey = context.params.id;
    const charge = {amount, currency, customer};
    if (val.source !== null) {
       charge.source = val.source;
    }
    const response = await stripe.charges
        .create(charge, {idempotency_key: idempotencyKey});
    // If the result is successful, write it back to the database
    return snap.ref.set(response, { merge: true });
    } catch(error) {
        await snap.ref.set({error: userFacingMessage(error)}, { merge: true });
    }
});




// initialize calender date collections in firestore, to keep track of booked hours
// exports.AddCoachCalenderDataToFirestoreAfterSigningUp = functions.auth.user().onCreate((user) => {
//     functions.firestore.collection("coaches").doc(user.data.uid).set({
//         fullname: "",
//         age: "",
//         location: "",
//         bio: ""
//     });
    // console.log(user.data);
    // userDoc = {'email' = user.data.email, 
    //            'displayName' = user.data.displayName}
    // admin.firestore().collection('users').doc(user.data.uid)
    // .set(userDoc).then(writeResult => {
    //     console.log('User Created result:', writeResult);
    //     return;
    // }).catch(err => {
    //    console.log(err);
    //    return;
    // });
// });

// delete user document from firestore if account is deleted
// exports.DeleteCoachDataFromFirestoreAfterDeletingAccount = functions.auth.user().onDelete((user) => {
//     functions.firestore.collection("coaches").doc(user.data.uid).delete()
// });
