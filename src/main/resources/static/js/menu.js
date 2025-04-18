document.addEventListener('DOMContentLoaded', function() {
    let currentItem = null;

    document.querySelectorAll('.menu-card').forEach(card => {
        card.addEventListener('click', function() {
            currentItem = {
                name: this.querySelector('.menu-item-name').textContent,
                price: parseFloat(this.querySelector('.menu-item-price').textContent.replace('$', '')),
                id: this.querySelector('.item-details').getAttribute('data-item-id')
            };

            document.getElementById('popup-item-id').value = currentItem.id;
            openPopup();
        });
    });

    function openPopup() {
        const quantityInput = document.getElementById('quantity');
        quantityInput.value = 1;
        updateTotalPrice(1);

        document.getElementById('popup-item-details').innerHTML = `
            <h3>${currentItem.name}</h3>
            <p>Price: $${currentItem.price.toFixed(2)}</p>
        `;

        quantityInput.oninput = () => updateTotalPrice(quantityInput.value);
        document.getElementById('popup-overlay').style.display = 'flex';
        document.body.style.overflow = 'hidden';
    }

    function updateTotalPrice(quantity) {
        document.getElementById('total-price').textContent =
            `Total: $${(currentItem.price * quantity).toFixed(2)}`;
    }

    function closePopup() {
        document.getElementById('popup-overlay').style.display = 'none';
        document.body.style.overflow = 'auto';
        currentItem = null;
    }

    document.querySelector('.close-btn').addEventListener('click', closePopup);
    document.getElementById('popup-overlay').addEventListener('click', function(e) {
        if (e.target === this) closePopup();
    });
});
