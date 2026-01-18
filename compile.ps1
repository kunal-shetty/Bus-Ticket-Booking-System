Write-Host "Compiling Bus Ticket Booking System..."

$JAVAFX_PATH = "lib/javafx/lib"

if (!(Test-Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
}

$javacArgs = @(
    "--module-path", $JAVAFX_PATH,
    "--add-modules", "javafx.controls,javafx.fxml",
    "-cp", "lib/mysql-connector-j.jar",
    "-d", "bin",
    "src/main/java/com/busbooking/app/Main.java",
    "src/main/java/com/busbooking/controller/*.java",
    "src/main/java/com/busbooking/dao/*.java",
    "src/main/java/com/busbooking/model/*.java",
    "src/main/java/com/busbooking/service/*.java",
    "src/main/java/com/busbooking/util/*.java"
)

javac @javacArgs

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed."
    exit
}

Write-Host "Copying resources..."
Copy-Item -Recurse -Force src/main/resources/* bin/

Write-Host "Running application..."

$javaArgs = @(
    "--module-path", $JAVAFX_PATH,
    "--add-modules", "javafx.controls,javafx.fxml",
    "-cp", "lib/mysql-connector-j.jar;bin",
    "com.busbooking.app.Main"
)

java @javaArgs
