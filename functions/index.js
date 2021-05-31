const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//

exports.sendNotification1 = functions.firestore.document("recipes/{recipes}").onWrite((change, context) => {
  const payload = {
    notification: {
      title: "New Recipe: ",
      body: "New Recipe Added to FireBase",
    },
  };
  // //const getDeviceTokenPromise = admin.firestore().collection("users");
  // //const results = Promise.all([getDeviceTokenPromise]);
  const token = "dkPimgtRSbOhq9MBYIi8n4:APA91bG8Vd2eTF7ssmBl93q83DXYsVEXjeNCo0GQJqrS6HIDOMsydkmcNX1B23BDBSs0Vx25U2NA9nKO2PWxfNpf1wTFx5-F8JfRc6ziTIyEm9FabhYUtWNLRB0gyp9qatpNbZN1HN55";
  return admin.messaging().sendToDevice(token, payload);
});
