// Get the modal1
var modal1 = document.getElementById('updateEntryModal');

// Get the button that opens the modal1
var btn1 = document.getElementById("updateEntrybtn");

// Get the <span1> element that closes the modal1
var span1 = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal1 
btn1.onclick = function() {
    modal1.style.display = "block";
}

// When the user clicks on <span1> (x), close the modal1
span1.onclick = function() {
    modal1.style.display = "none";
}

// When the user clicks anywhere outside of the modal1, close it
window.onclick = function(event) {
    if (event.target == modal1) {
        modal1.style.display = "none";
    }
}