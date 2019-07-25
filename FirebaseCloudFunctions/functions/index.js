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
