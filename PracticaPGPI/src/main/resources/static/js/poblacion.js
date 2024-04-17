document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/productos')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('productTableBody');
            data.forEach(product => {
                let row = `<tr>
                               <td>${product.campaignName}</td>
                               <td>${product.productName}</td>
                               <td>${product.quantityInStock}</td>
                               <td>${product.deliveryTime}</td>
                           </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error loading the products:', error));
});