"use strict";
alert(":)))))))))))))");

// Function to find percentage
function percentage(a, b) {
  return a / b * 100 > 100 ? 100 : a / b * 100;
}

//On Window Load
window.onload = function() {

  //User Object
  var User = {
    name: "rev",
    donationCollect: document.getElementById("doncol").textContent,
    donationGoal: document.getElementById("dongoal").textContent
  };

  // IDs
  var donationUser = document.getElementById("donation--user"),
    donationProgress = document.getElementById("donation--progress"),
    donationNumber = document.getElementById("donation--number"),
    donationGoal = document.getElementById("donation--goal"),
    donationStatus = document.getElementById("donation--status"),
    donationAmount = document.getElementById("donation--amount"),
    donate = document.getElementById("donate");

  // How much percent to reach Goal
  var percent = percentage(User.donationCollect, User.donationGoal);
  // What we have so far to reach Goal
  donationProgress.setAttribute("aria-valuenow", User.donationCollect);
  // Goal
  donationProgress.setAttribute("aria-valuemax", User.donationGoal);

  // Default Data
  donationUser.innerHTML = "Progress";
  donationProgress.setAttribute("style", "width:" + percent + "%");
  donationNumber.setAttribute("style", "left:" + percent + "%");
  donationNumber.innerHTML = "$" + User.donationCollect;
  donationGoal.innerHTML = "Goal<br>$" + User.donationGoal;
  donationStatus.innerHTML = "<i class='fa fa-window-close red'></i> You need <span class='red'>$" + (User.donationGoal - User.donationCollect) + "</span> to reach your Donation Goal";

  //Events

  //Only Positive Numbers allow in the Donation input
  donationAmount.onkeydown = function(e) {
    if (!((e.keyCode > 95 && e.keyCode < 106) || (e.keyCode > 47 && e.keyCode < 58) || e.keyCode === 8)) {
      return false;
    }
  };

  // Onclick event for the donate button
  donate.onclick = function(e) {
    e.preventDefault();
    var newDonationCollect = (+User.donationCollect) + (+donationAmount.value);
    var newPercent = percentage(newDonationCollect, User.donationGoal);
    var newDonationNumber = User.donationCollect != User.donationGoal ? (+User.donationCollect) + (+donationAmount.value) : User.donationGoal;
    User.donationCollect = newDonationNumber;
    donationNumber.innerHTML = "$" + newDonationNumber;
    donationProgress.setAttribute("style", "width:" + newPercent + "%");
    donationNumber.setAttribute("style", "left:" + newPercent + "%");
    donationStatus.innerHTML = User.donationGoal - newDonationNumber > 0 ? "<i class='fa fa-window-close red'></i> You need <span class='red'>$" + (User.donationGoal - newDonationNumber) + "</span> to reach your Donation Goal" : "<i class='fa fa-exclamation-circle green'></i> Your campaign has been funded";

  };

};