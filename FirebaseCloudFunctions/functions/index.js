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


// initialize calender date collections in firestore, to keep track of booked hours
exports.AddCoachCalenderDataToFirestoreAfterSigningUp = functions.auth.user().onCreate((user) => {
    functions.firestore.collection("coaches").doc(user.data.uid).set({
        fullname: "",
        age: "",
        location: "",
        bio: ""
    });
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
});
