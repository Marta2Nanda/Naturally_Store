// The root URL for the RESTful services
var rootURL = "http://localhost:8080/naturally_store/rest/products";

var currentProduct;

$(document).ready(function() {
findAll();

$('#btnAdd').click(function() {
	resetForm();
});

$('#btnSave').click(function() {
	if ($('#productCode').val() == ''){
		addProduct();
	}
	else{
		updateProduct();
	}
});

$('#btnDelete').click(function() {
	deleteProduct();
	resetForm();
});

$(document).on("click", "#table_id tr", function() {
	findById(this.id);
});
});

function findAll() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", 
		success: renderList
	});
};

function addProduct() {
	console.log('addProduct');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			$('#success').modal('show');
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			$("#error").modal('show');
		}
	});
};

function deleteProduct() {
	console.log('deleteProduct');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#productCode').val(),
		success: function(data, textStatus, jqXHR){
				  $('#success').modal('show');
				  findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			 $("#error").modal('show');
		}
	});
};

function updateProduct() {
	console.log('updateWine');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL + '/' + $('#productCode').val(),
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			$('#success').modal('show');
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			 $("#error").modal('show');
		}
	});
};
function findById(productCode) {
	console.log('findById: ' + productCode);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + productCode,
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
			currentProduct = data;
			renderDetails(currentProduct);
		}
	});
};


function renderList(data) {
	$('#table_body').empty();
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	$.each(list, function(index, product) {
		$('#table_body').append('<tr id="'+product.productCode+'">'+'<td>'+product.name+'</td><td>'+product.manufacturer+'</td><td>'
				+product.suitableFor+'</td><td>'+product.skinType+'</td><td>'+product.capacity+'</td><td><a id="edit" href="#"data-toggle="modal" data-target="#exampleModal"><span class="fa fa-pencil">Edit</a></td></tr>');
	});
	$('#table_id').DataTable();
	output='<div class="row">';
	$('.row').empty();
	$.each(list, function(index, product){
		output+=('<div class="col-sm-6 col-md-4 col-lg-3"><div class="card"><img id="'+product.productCode+'" class="productImage" onclick="search('+product.productCode+')" src="resources/pics/'+product.picture
				+'" height="150"><p>Name: '+product.name+'<p>Product code: '+product.productCode+'</p><p>Manufacturer: '+product.manufacturer
				+'</p><p>Capacity: '+product.capacity+'</p></div></div>')
	});
	output+='</div>';
	$('#productList').append(output);
};

function renderDetails(product) {
	$('#productCode').val(product.productCode);
	$('#name').val(product.name);
	$('#manufacturer').val(product.manufacturer);
	$('#suitableFor').val(product.suitableFor);
	$('#skinType').val(product.skinType);
	$('#capacity').val(product.capacity);
	$('#pic').attr('src', 'resources/pics/' + product.picture);
	$('#description').val(product.description);
}
var search = function(searchKey){
	if(searchKey==''){
	}
	else{
		searchDescription(searchKey);
	}
}
function searchDescription(productCode){
	console.log('search: ' + productCode);
	$.ajax({
		type: 'GET',
		url: rootURL +'/'+ productCode,
		dataType: "json",
		success: function(data){
			console.log('Description success: '+productCode);
			$('#DescriptionModal-body').append('<p>'+data.description+'</p>');
			$('#Description').modal('show');
		}
	});
}
function formToJSON() {
	var productCode = $('#productCode').val();
	return JSON.stringify({
		"productCode": productCode == "" ? null : productCode, 
		"name": $('#name').val(), 
		"manufacturer": $('#manufacturer').val(),
		"suitableFor": $('#suitableFor').val(),
		"skinType": $('#skinType').val(),
		"capacity": $('#capacity').val(),
		"picture": $('#picture').val(),
		"description": $('#description').val()
		});
}
var resetForm = function() {
	$('#productCode').val("");
	$('#name').val("");
	$('#manufacturer').val("");
	$('#suitableFor').val("");
	$('#skinType').val("");
	$('#capacity').val("");
	$('#picture').val("");
	$('#description').val("");
}  