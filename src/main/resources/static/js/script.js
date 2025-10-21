// src/main/resources/static/js/script.js

document.addEventListener('DOMContentLoaded', (event) => {
    console.log('Cafeteria Tracker: Frontend scripts loaded.');

    // --- Sales Form AJAX Submission Logic ---
    const salesForm = document.getElementById('sales-form');
    if (salesForm) {
        salesForm.addEventListener('submit', function(e) {
            e.preventDefault(); // Stop the standard form submission

            const messageDiv = document.getElementById('message-area');
            messageDiv.textContent = 'Recording sale...';
            messageDiv.style.color = 'blue';

            // 1. Collect form data
            const formData = new FormData(salesForm);
            const saleData = {
                userId: formData.get('userId'),
                itemId: formData.get('itemId'),
                quantity: parseInt(formData.get('quantity'))
            };

            // 2. Send POST request to the REST API
            fetch('/api/sales', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(saleData),
            })
                .then(response => {
                    // Check for successful status (e.g., 201 Created)
                    if (!response.ok) {
                        throw new Error('Failed to record sale. Status: ' + response.status);
                    }
                    return response.json(); // Parse the response body as JSON
                })
                .then(data => {
                    // 3. Success: Display message and reset form
                    console.log('Sale recorded successfully:', data);
                    messageDiv.textContent = `Sale recorded! Total: $${data.totalAmount.toFixed(2)}`;
                    messageDiv.style.color = 'green';
                    salesForm.reset(); // Clear the form fields
                })
                .catch(error => {
                    // 4. Error: Display error message
                    console.error('Error recording sale:', error);
                    messageDiv.textContent = 'Error: Could not record sale. Check user/item IDs.';
                    messageDiv.style.color = 'red';
                })
                .finally(() => {
                    // Clear the message after a few seconds
                    setTimeout(() => messageDiv.textContent = '', 5000);
                });
        });
    }
    // --- End Sales Form Logic ---

});