// Call the dataTables jQuery plugin
$(document).ready(function() {
	loadUsers();
	$('#users').DataTable();
	
	updateEmailUser();
	
});


function updateEmailUser() {
	document.getElementById('txtEmailUser').outerHTML = localStorage.email;
}


async function loadUsers() {

	const request = await fetch('api/users', {
		method: 'GET',
		headers:  getHeaders() 
	});
	const users = await request.json();


	let listHtml = '';

	for (let user of users) {

		let buttonDelete = '<a href="#" onclick="deleteUser(' + user.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>'

		let phoneNumberNotNull = user.phone == null ? '-' : user.phone;

		let userHtml = '<tr><td>' + user.id + '</td><td>' + user.name + ' ' + user.surname + '</td><td>' + user.email + '</td><td>' + phoneNumberNotNull + '</td><td>' + buttonDelete + '</td></tr>'

		listHtml += userHtml;
	}

	console.log(users);

	document.querySelector('#users tbody').outerHTML = listHtml;


}

function getHeaders() {
	return {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
		'Authorization': localStorage.token
	};
}

async function deleteUser(id) {

	if (!confirm('¿Desea eliminar el usuario?')) {
		return;
	}

	const request = await fetch('api/users/' + id, {
		method: 'DELETE',
		headers: getHeaders()
	});

	location.reload();

}