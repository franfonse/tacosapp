window.addEventListener('DOMContentLoaded', () => {
    const deletePopup = document.getElementById('delete-popup');
    const editPopup = document.getElementById('edit-popup');
    const checkoutPopup      = document.getElementById('checkout-popup');
    const checkoutBtn        = document.getElementById('checkout-button');
    const cancelCheckoutBtn  = document.getElementById('cancel-checkout');
    let selectedItemId = null;
    let selectedItemEl = null;

    // Delete item flow
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', e => {
            selectedItemEl = e.target.closest('.order-item');
            selectedItemId = selectedItemEl.getAttribute('data-item-id');
            document.getElementById('delete-item-id').value = selectedItemId;
            document.getElementById('delete-item-name').textContent = selectedItemEl.getAttribute('data-item-name');
            deletePopup.style.display = 'flex';
        });
    });

    document.getElementById('cancel-delete').addEventListener('click', () => {
        deletePopup.style.display = 'none';
        selectedItemId = null;
    });

    // Edit quantity flow
    document.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', e => {
            selectedItemEl = e.target.closest('.order-item');
            selectedItemId = selectedItemEl.getAttribute('data-item-id');
            document.getElementById('edit-item-id').value = selectedItemId;
            document.getElementById('edit-item-name').textContent = selectedItemEl.getAttribute('data-item-name');
            document.getElementById('edit-quantity').value = selectedItemEl.getAttribute('data-item-quantity');
            editPopup.style.display = 'flex';
        });
    });

    document.getElementById('cancel-edit').addEventListener('click', () => {
        editPopup.style.display = 'none';
        selectedItemId = null;
    });

    // Open checkout popup
    checkoutBtn.addEventListener('click', () => {
        checkoutPopup.style.display = 'flex';
        document.body.style.overflow  = 'hidden';
    });

    // Cancel checkout
    cancelCheckoutBtn.addEventListener('click', () => {
        checkoutPopup.style.display = 'none';
        document.body.style.overflow  = 'auto';
    });

});
