document.addEventListener('DOMContentLoaded', () => {
    let currentItem = null;

    // Open item popup
    document.querySelectorAll('.menu-card').forEach(card => {
        card.addEventListener('click', () => {
            currentItem = {
                name: card.querySelector('.menu-item-name').textContent,
                price: parseFloat(card.querySelector('.menu-item-price').textContent.replace('$', '')),
                id: card.querySelector('.item-details').getAttribute('data-item-id')
            };

            document.getElementById('popup-item-id').value = currentItem.id;
            openPopup();
        });
    });

    function openPopup() {
        const overlay = document.getElementById('popup-overlay');
        const quantityInput = document.getElementById('quantity');
        const detailsContainer = document.getElementById('popup-item-details');

        quantityInput.value = 1;
        updateTotalPrice(1);

        detailsContainer.innerHTML = `
            <h3>${currentItem.name}</h3>
            <p>Price: $${currentItem.price.toFixed(2)}</p>
        `;

        quantityInput.oninput = () => updateTotalPrice(quantityInput.value);

        overlay.style.display = 'flex';
        document.body.style.overflow = 'hidden';
    }

    function updateTotalPrice(quantity) {
        const totalPriceEl = document.getElementById('total-price');
        totalPriceEl.textContent = `Total: $${(currentItem.price * quantity).toFixed(2)}`;
    }

    function closePopup() {
        const overlay = document.getElementById('popup-overlay');
        overlay.style.display = 'none';
        document.body.style.overflow = 'auto';
        currentItem = null;
    }

    document.querySelector('.close-btn').addEventListener('click', closePopup);
    document.getElementById('popup-overlay').addEventListener('click', e => {
        if (e.target === e.currentTarget) closePopup();
    });
});
