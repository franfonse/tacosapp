window.addEventListener('DOMContentLoaded', () => {
    const deletePopup = document.getElementById('delete-popup');
    const editPopup = document.getElementById('edit-popup');
    let selectedItemId = null;
    let selectedItemEl = null;

    // Delete item flow
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', e => {
            selectedItemEl = e.target.closest('.order-item');
            selectedItemId = selectedItemEl.getAttribute('data-item-id');
            document.getElementById('delete-item-name').textContent = selectedItemEl.getAttribute('data-item-name');
            deletePopup.style.display = 'flex';
        });
    });

    document.getElementById('cancel-delete').addEventListener('click', () => {
        deletePopup.style.display = 'none';
        selectedItemId = null;
    });

    document.getElementById('confirm-delete').addEventListener('click', () => {
        fetch(`/orderItem/delete?orderItemId=${selectedItemId}`, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                selectedItemEl.remove();
            } else {
                alert('Error deleting item');
            }
            deletePopup.style.display = 'none';
        }).catch(err => {
            console.error(err);
            deletePopup.style.display = 'none';
        });
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
});
