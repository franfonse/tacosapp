document.addEventListener('DOMContentLoaded', function() {
    // Add click event to all menu cards
    const menuCards = document.querySelectorAll('.menu-card');
    menuCards.forEach(card => {
        card.addEventListener('click', function() {
            const itemDetails = this.querySelector('.item-details').innerHTML;
            openPopup(itemDetails);
        });
    });

    // Add click event to close button
    document.querySelector('.close-btn').addEventListener('click', closePopup);

    // Close popup when clicking outside the content
    document.getElementById('popup-overlay').addEventListener('click', function(e) {
        if (e.target === this) {
            closePopup();
        }
    });
});

function openPopup(content) {
    // Set the content in the popup
    document.getElementById('popup-item-details').innerHTML = content;

    // Display the popup
    document.getElementById('popup-overlay').style.display = 'flex';

    // Prevent body scrolling when popup is open
    document.body.style.overflow = 'hidden';
}

function closePopup() {
    // Hide the popup
    document.getElementById('popup-overlay').style.display = 'none';

    // Restore body scrolling
    document.body.style.overflow = 'auto';
}
