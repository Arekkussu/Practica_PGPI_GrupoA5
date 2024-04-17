document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        // Validaciones
        const campaignName = document.getElementById('campaignName').value;
        const productName = document.getElementById('productName').value;
        const quantityInStock = document.getElementById('quantityInStock').value;
        const deliveryTime = document.getElementById('deliveryTime').value;

        let isValid = true;

        // Ejemplo de validaciones simples, podrían ser más complejas dependiendo de tus requisitos
        if (!campaignName) {
            showModal('Error', 'Por favor, ingresa el nombre de la campaña');
            isValid = false;
        }
        if (!productName) {
            showModal('Error', 'Por favor, ingresa el nombre del producto');
            isValid = false;
        }
        if (!quantityInStock || quantityInStock < 1) {
            showModal('Error', 'Por favor, ingresa la cantidad del producto');
            isValid = false;
        }
        if (!deliveryTime) {
            showModal('Error', 'Por favor, ingresa el tiempo de entrega del producto');
            isValid = false;
        }

        // Si todos los campos son válidos, procedemos a enviar la solicitud
        if (isValid) {
            // Crea un objeto con la información para enviar
            const productInfo = {
                campaignName: campaignName,
                productName: productName,
                quantityInStock: quantityInStock,
                deliveryTime: deliveryTime
            };

            // Hacer la solicitud POST
            fetch('/api/productos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(productInfo)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('La respuesta del servidor no fue OK');
                    }
                    return response.json(); // Aquí asumimos que la respuesta debe ser JSON
                })
                .then(data => {
                    if(data.success) {
                        showModal('Éxito', 'Producto ingresado con éxito');
                    } else {
                        showModal('Error', 'Hubo un problema a la hora de registrar el producto');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showModal('Error', 'Error al realizar la solicitud: ' + error.message);
                });
        }
    });

    function showModal(title, message) {
        document.getElementById('modalLabel').textContent = title;
        document.getElementById('modalMessage').textContent = message;
        const modalButton = document.getElementById('modalButton');

        // Configura el evento click para cerrar el modal
        modalButton.onclick = function() {
            $('#messageModal').modal('hide');
        };

        $('#messageModal').modal('show');
    }
});