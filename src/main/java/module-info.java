module com.example.knapsnack_problem_fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.knapsnack_problem_fx to javafx.fxml;
    exports com.knapsnack_problem_fx;
}