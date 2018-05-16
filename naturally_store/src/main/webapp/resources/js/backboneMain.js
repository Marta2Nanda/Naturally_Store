//------------------------global variables
var products;
var productsView;
var productsTableView;
var productModalView;
var product;
var selectRowData;
var rootURL = "http://localhost:8080/naturally_store/rest/products";
//-------------------------method
var Product = Backbone.Model.extend({
	initialize: function(){
	},
	urlRoot:rootURL
	
});
var Products = Backbone.Collection.extend({
		model: Product,
		url: rootURL
	});

var ProductView = Backbone.View.extend({
	tagName : "div",
	className : "col-sm-6 col-md-4 col-lg-3",
	render : function() {
		var template = _.template($("#productCardTemplate").html());
		var html = template(this.model.toJSON());
		this.$el.html(html);
		return this;
	}
});

var ProductsView = Backbone.View.extend({
	render : function() {
		this.collection.each(function(product) {
			var productView = new ProductView({
				model : product
			});
			this.$el.append(productView.render().el);
		}, this);
	}
});

var ProductsTableView = Backbone.View.extend({
			initialize : function() {
			},
			render : function() {
				products = new Products();
				products.fetch({
							success : function(products) {
								$('#table_id').DataTable(
												{
													data : products.toJSON(),
													columns:  [
										    			{"data" : "productCode"},
										    			{"data" : "name"},
										    			{"data" : "manufacturer"},
										    			{"data" : "suitableFor"},
										    			{"data" : "skinType"},
										    			{"data" : "capacity"},
										    			{"defaultContent" :'<a id="edit" href="#"data-toggle="modal" data-target="#exampleModal"><span class="fa fa-pencil">Edit</a>'
										    	}]
															
								});

					}
			});
		}
	});
var ProductEditModalView = Backbone.View.extend({
	render : function() {
		var template = _.template($("#productModalTemplate").html());
		var html = template(this.model.toJSON());
		this.$el.html(html);
		return this;
	}
});

var loadTable = function() {
	productsTableView = new ProductsTableView();
	productsTableView.render();
};

//----------------------------page load DOM
$(document).ready(function() {
	loadTable();
			$("#productsLink").click(function() {
				$('#productList').html('');
				products = new Products();
				products.fetch({
					success : function(data) {
						productsView = new ProductsView({
							el : "#productList",
							collection : products
						});
						productsView.render();
					}
				});
			});

			$('#table_id').on('click', 'a', function() {
						selectRowData = $('#table_id').DataTable().row(
								$(this).parents('tr')).data();
						product = new Product(selectRowData);
						productModalView = new ProductEditModalView({
							el : "#modalBody",
							model : product
						});
						productModalView.render();
						$('#btnDelete').prop('disabled', false);
						$('#btnAdd').prop('disabled', false);
						$('#btnSave').prop('disabled', false);
					});

			$('#btnAdd').click(function() {
				product = new Product({
					productCode : "",
					name : "",
					manufacturer : "",
					suitableFor : "",
					skinType : "",
					capacity : "",
					picture : "",
					description : ""
				});
				productModalView = new ProductEditModalView({
					el : "#modalBody",
					model: product
				});
				productModalView.render();
			});

			$('#btnSave').click(function() {
				if ($('#productCode').val() == ''){
					product = new Product({
						name: $('#name').val(),
						manufacturer: $('#manufacturer').val(),
						suitableFor: $('#suitableFor').val(),
						skinType: $('#skinType').val(),
						capacity: $('#capacity').val(),
						picture: $('#pic').val(),
						description: $('#description').val()
						});
						product.save({}, {
							type : 'POST',
							success : function() {
								$('#table_id').DataTable().destroy();
								productsTableView.render();
								$('#success').modal('show');
							},
							error : function() {
								console.log("error with creating new product")
								 $("#error").modal('show');
							}
						});
				}
				else{
					product = new Product({
					productCode : $('#productCode').val().trim(),
					name: $('#name').val(),
					manufacturer: $('#manufacturer').val(),
					suitableFor: $('#suitableFor').val(),
					skinType: $('#skinType').val(),
					capacity: $('#capacity').val(),
					picture: $('#pic').val(),
					description: $('#description').val()
				});
				product.save({}, {
					success : function() {
						type : 'PUT',
						$('#table_id').DataTable().destroy();
						productsTableView.render();
						$('#success').modal('show');
					},
					error : function() {
						console.log("error with updating product")
						 $("#error").modal('show');
					}
				});
				}
				});

			$('#btnDelete').click(function() {
				product = new Product({
					id : $('#productCode').val().trim()
				});
				
				product.destroy({
					success : function() {
						$('#table_id').DataTable().destroy();
						productsTableView.render();
						$('#success').modal('show');
					},
					error : function() {
						console.log("error with deleting product")
						 $("#error").modal('show');
					}
				});
			});

		});