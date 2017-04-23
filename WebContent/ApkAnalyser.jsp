<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>jstree basic demos</title>
<style>
html {
	margin: 0;
	padding: 0;
	font-size: 62.5%;
}

body {
	max-width: 800px;
	min-width: 300px;
	margin: 0 auto;
	padding: 20px 10px;
	font-size: 14px;
	font-size: 1.4em;
}

h1 {
	font-size: 1.8em;
}

.demo {
	overflow: auto;
	border: 1px solid silver;
	min-height: 100px;
}
</style>
<link rel="stylesheet" href="themes/default/style.min.css" />
</head>
<body>
	<h1>APK demo</h1>

	<div id="evts" class="demo"></div>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="js/jstree.min.js"></script>

	<script>
		// html demo
		$(document)
				.ready(
						function() {
							$("#uploadbutton1").click(function() {
								var filename = $("#file").val();

								$.ajax({
									type : "POST",
									url : "uploadFile",
									data : {
										file : filename
									},
									success : function() {
										alert("Data Uploaded: ");
									},
									error : function() {
										alert("Data upload failed.")
									}
								});
							});
							$('#html').jstree();

							// interaction and events
							$('#evts_button').on("click", function() {
								var instance = $('#evts').jstree(true);
								instance.deselect_all();
								instance.select_node('1');
							});
							$('#evts')
									.on(
											"changed.jstree",
											function(e, data) {
												if (data.selected.length) {
													reply = window
															.confirm('You have selected: '
																	+ data.instance
																			.get_node(data.selected[0]).text
																	+ "\n Do you want to replace it?");
													if (reply)
														$("#myModal").modal(
																"show")
												}
											}).jstree({
										'core' : {
											'multiple' : false,
											'data' : {
												"url" : "flare.json",
												"dataType" : "json"
											}
										}
									});
						});
	</script>
	<!-- Modal -->

	<div id="myModal" class="modal fade" role="dialog"
		style="display: none">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Upload File you want to replace.</h4>
				</div>
				<div class="modal-body">
					<form method="POST" action="uploadFile"
						enctype="multipart/form-data">
						<p>
							<input type="file" name="file" id="file" />
						</p>
						<br />

						<button type="submit" class="btn btn-primary" data-dismiss="modal"
							id="uploadbutton">Upload</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</form>
				</div>
			</div>

		</div>
	</div>

</body>
</html>