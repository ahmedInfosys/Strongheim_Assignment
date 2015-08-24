<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Student fields</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<form action="modifyTable" method="get">
<div class="form-group col-xs-5">
 <h3>Modify Grade</h3>
 
 		<select class="form-control" id="class" name="class">
			${classes}
		</select>
		<select class="form-control" id="studentID" name="studentID">
			${IDs}
		</select>
		<select class="form-control" id="Assignment" name="Assignment">
			${Assignments}
		</select>
		<button type="submit" class="btn btn-default" >Look Up!</button>
</div>
</form>
<form action="change" method="get">

	${table}

</form>
</body>

</html>