package assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Assignment extends Application {

    private static Point2D point[][];
    private int noOfPoints;
    private TableView table = new TableView();
    static ObservableList<Point> points = FXCollections.observableArrayList();
    private TableView pathTable = new TableView();
    private TableView kangarooTable = new TableView();
    static ObservableList<Point> pathList = FXCollections.observableArrayList();
    static ObservableList<PointPath> pathTableList = FXCollections.observableArrayList();
    static ObservableList<Kangaroo> kangarooList = FXCollections.observableArrayList();
    private int MAXKANGAROO;
    private int noOfRow;
    private int POINTNUMBER;
    private static int colonyThreshold = 0;
    private static int sumOfColony = 0;
    private static int startPathX, startPathY, endPathX, endPathY;
    private static boolean endRun = false;

    @Override
    public void start(Stage primaryStage) {

        Button btn = new Button();
        btn.setText("Start!");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog td1 = new TextInputDialog();
                td1.setTitle("Number Of Point(s)");
                td1.setHeaderText("Enter number of points");
                td1.showAndWait();
                Stage stage = new Stage();
                try {
                    noOfPoints = Integer.parseInt(td1.getEditor().getText());
                } catch (NumberFormatException e) {
                    Alert exception = new Alert(Alert.AlertType.WARNING);
                    exception.setTitle("Error Message");
                    exception.setHeaderText("Invalid Input");
                    exception.showAndWait();
                    stage.close();
                    return;
                }
                td1.close();

                TableColumn id = new TableColumn("ID");
                TableColumn food = new TableColumn("No of food");
                TableColumn size = new TableColumn("Max Kangaroo Size");
                TableColumn path = new TableColumn("No of Paths Connected");
                id.setMinWidth(50);
                food.setMinWidth(150);
                size.setMinWidth(200);
                path.setMinWidth(250);
                id.setCellValueFactory(new PropertyValueFactory<Point, String>("id"));
                food.setCellValueFactory(new PropertyValueFactory<Point, String>("food"));
                size.setCellValueFactory(new PropertyValueFactory<Point, Integer>("size"));
                path.setCellValueFactory(new PropertyValueFactory<Point, Integer>("path"));
                table.setEditable(true);
                id.setCellFactory(TextFieldTableCell.forTableColumn());
                id.setOnEditCommit(
                        new EventHandler<CellEditEvent<Point, String>>() {
                    @Override
                    public void handle(CellEditEvent<Point, String> t) {
                        ((Point) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setId(t.getNewValue());
                    }
                }
                );
//                food.setCellFactory(TextFieldTableCell.forTableColumn());
//                food.setOnEditCommit(
//                        new EventHandler<CellEditEvent<Point, String>>() {
//                    @Override
//                    public void handle(CellEditEvent<Point, String> t) {
//                        Integer val = Integer.parseInt(t.getNewValue());
//                        ((Point) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())).setFood(val);
//                    }
//                }
//                );
//                size.setCellFactory(TextFieldTableCell.forTableColumn());
//                size.setOnEditCommit(
//                        new EventHandler<CellEditEvent<Point,String>>(){
//                            @Override
//                            public void handle(CellEditEvent<Point,String>t){
//                                ((Point)t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())).setSize(Integer.parseInt(t.getNewValue()));
//                            }
//                        }
//                );
//                path.setCellFactory(TextFieldTableCell.forTableColumn());
//                path.setOnEditCommit(
//                        new EventHandler<CellEditEvent<Point,String>>(){
//                            @Override
//                            public void handle(CellEditEvent<Point,String>t){
//                                ((Point)t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())).setPath(Integer.parseInt(t.getNewValue()));
//                            }
//                        }
//                );
                TextField idinput = new TextField();
                TextField foodinput = new TextField();
                TextField sizeinput = new TextField();
                TextField pathinput = new TextField();
                idinput.setPromptText("ID");
                foodinput.setPromptText("No of Food");
                sizeinput.setPromptText("Max Kangaroo Size");
                pathinput.setPromptText("No of Paths Connected");
                idinput.setMaxWidth(50);
                foodinput.setPrefWidth(100);
                sizeinput.setMinWidth(130);
                pathinput.setMinWidth(150);
                Button add = new Button("Add");
                HBox hb = new HBox();
                POINTNUMBER = noOfPoints;

                add.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        if (idinput.getText().equals("")
                                || pathinput.getText().equals("")
                                || foodinput.getText().equals("")
                                || sizeinput.getText().equals("")) {
                            Alert incomplete = new Alert(Alert.AlertType.WARNING);
                            incomplete.setTitle("Error");
                            incomplete.setHeaderText("Incomplete data");
                            incomplete.showAndWait();
                            return;
                        }

                        if (noOfPoints < 1) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attention!");
                            alert.setHeaderText("Exceeded number of points");
                            alert.showAndWait();
                            return;
                        }
                        for (int i = 0, a = points.size(); i < a; i++) {
                            if (points.get(i).getId().equals(idinput.getText())) {
                                //Check for redundant path ID
                                Alert redundant = new Alert(Alert.AlertType.WARNING);
                                redundant.setTitle("Error");
                                redundant.setHeaderText("Redundant Path ID");
                                redundant.showAndWait();
                                idinput.clear();
                                foodinput.clear();
                                sizeinput.clear();
                                pathinput.clear();
                                return;
                            }
                        }
                        if (Integer.parseInt(pathinput.getText()) >= POINTNUMBER) {
                            //Check whether path exceed point or not
                            //Max path for n point is n-1
                            Alert exceeded = new Alert(Alert.AlertType.WARNING);
                            exceeded.setTitle("Error");
                            exceeded.setHeaderText("Number of path should be less than total number of points which is " + POINTNUMBER);
                            exceeded.showAndWait();
                            pathinput.clear();
                            return;
                        }
                        points.add(new Point(idinput.getText(),
                                Integer.parseInt(foodinput.getText()),
                                Integer.parseInt(sizeinput.getText()),
                                Integer.parseInt(pathinput.getText())));
                        idinput.clear();
                        foodinput.clear();
                        sizeinput.clear();
                        pathinput.clear();
                        noOfPoints--;
                        noOfRow = id.getTableView().getItems().size();
                    }
                });
                Button ok = new Button("OK");
                hb.getChildren().addAll(idinput, foodinput, sizeinput,
                        pathinput, add, ok);
                hb.setSpacing(5);
                ok.setOnAction(new EventHandler<ActionEvent>() {

                    private int totalAllPointsKangaroo = 0;

                    private int current = 0;
                    //index of current point

                    private int entry = 0;
                    //entry is the number of path for each point

                    @Override
                    public void handle(ActionEvent e) {
                        stage.close();
                        if (noOfRow == POINTNUMBER) {
                            Stage pathstage = new Stage();
                            HBox pathhb = new HBox();
                            TableColumn sourceid = new TableColumn("Source ID");
                            TableColumn link = new TableColumn("Linked to");
                            TableColumn pathid = new TableColumn("Path ID");
                            TableColumn obstacleheight = new TableColumn("Obstacle Height");

                            sourceid.setCellValueFactory(new PropertyValueFactory<PointPath, String>("source"));
                            link.setCellValueFactory(new PropertyValueFactory<PointPath, Point>("p"));
                            pathid.setCellValueFactory(new PropertyValueFactory<PointPath, String>("pathid"));
                            obstacleheight.setCellValueFactory(new PropertyValueFactory<PointPath, Integer>("obstacleheight"));

                            sourceid.setMinWidth(150);
                            link.setMinWidth(200);
                            pathid.setMinWidth(100);
                            obstacleheight.setMinWidth(200);

                            pathTable.setEditable(true);

                            TextField sourceidinput = new TextField();
                            TextField ptconnected = new TextField();
                            TextField ptid = new TextField();
                            TextField obstacleheightinput = new TextField();

                            ptconnected.setPromptText("Point Connected");
                            ptid.setPromptText("Path ID");
                            obstacleheightinput.setPromptText("Obstacle Height");

                            Button addpath = new Button("Add path");
                            while (points.get(current).getPath() == 0) {
                                //move to next point if the point has 0 path
                                current++;
                            }

                            sourceidinput.setText(points.get(current).getId());

                            sourceidinput.setEditable(false);

                            Button ok = new Button("OK");
                            ok.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    pathstage.close();
                                    TextInputDialog noOfKangaroo = new TextInputDialog();
                                    noOfKangaroo.setTitle("Number Of Kangaroo(s)");
                                    noOfKangaroo.setHeaderText("Enter the number of Kangaroo");
                                    noOfKangaroo.showAndWait();

                                    for (int i = 0; i < points.size(); i++) {
                                        totalAllPointsKangaroo += points.get(i).getSize();
                                    }

                                    MAXKANGAROO = Integer.parseInt(noOfKangaroo.getEditor().getText());
                                    System.out.println("total all points kangaroo : " + totalAllPointsKangaroo);
                                    while (MAXKANGAROO > totalAllPointsKangaroo) {
                                        Alert exceed = new Alert(Alert.AlertType.WARNING);
                                        exceed.setTitle("Error");
                                        exceed.setHeaderText("Exceeded " + (MAXKANGAROO - totalAllPointsKangaroo) + " kangaroo(s)");
                                        exceed.showAndWait();
                                        noOfKangaroo.getEditor().clear();
                                        noOfKangaroo.showAndWait();
                                        MAXKANGAROO = Integer.parseInt(noOfKangaroo.getEditor().getText());
                                    }

                                    TableColumn startPoint, gender, noOfFood;
                                    startPoint = new TableColumn("Initial Point");
                                    gender = new TableColumn("Gender");
                                    noOfFood = new TableColumn("Maximum Food in Pouch");
                                    startPoint.setCellValueFactory(new PropertyValueFactory<Kangaroo, Point>("startPoint"));
                                    gender.setCellValueFactory(new PropertyValueFactory<Kangaroo, String>("gender"));
                                    noOfFood.setCellValueFactory(new PropertyValueFactory<Kangaroo, Integer>("maxPouchFood"));
                                    startPoint.setMinWidth(250);
                                    gender.setMinWidth(150);
                                    noOfFood.setMinWidth(250);

                                    TextField startPointInput = new TextField();
                                    TextField genderInput = new TextField();
                                    TextField noOfFoodInput = new TextField();
                                    startPointInput.setPromptText("Initial Point of Kangaroo");
                                    genderInput.setPromptText("Gender of Kangaroo");
                                    noOfFoodInput.setPromptText("Maximum Food in Pouch");
                                    Button ok = new Button("OK");
                                    ok.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent e) {
                                            int totalKangaroo = 0;
                                            for (int i = 0; i < points.size(); i++) {
                                                totalKangaroo += points.get(i).getCurrentKangarooNumber();
                                            }
                                            System.out.println("current kangaroo number : " + totalKangaroo);
                                            if (totalKangaroo < MAXKANGAROO) {
                                                Alert notEnough = new Alert(Alert.AlertType.WARNING);
                                                notEnough.setTitle("Error");
                                                notEnough.setHeaderText("Need to input " + (MAXKANGAROO - totalKangaroo) + " more kangaroo(s)");
                                                notEnough.showAndWait();
                                                return;
                                            }
                                            TextInputDialog colony = new TextInputDialog();
                                            colony.setTitle("Colony");
                                            colony.setHeaderText("Enter the threshold for colony : ");
                                            colony.showAndWait();
                                            colonyThreshold = Integer.parseInt(colony.getEditor().getText());

                                            start();
                                        }
                                    });
                                    Button addInput = new Button("Add");
                                    addInput.setOnAction(new EventHandler<ActionEvent>() {

                                        private int count = 0;

                                        @Override
                                        public void handle(ActionEvent e) {
                                            Point start = null;
                                            boolean error = false;
                                            //Check the gender of kangaroo
                                            if (!genderInput.getText().equalsIgnoreCase("Male")
                                                    && !genderInput.getText().equalsIgnoreCase("Female")
                                                    && !genderInput.getText().equalsIgnoreCase("M")
                                                    && !genderInput.getText().equalsIgnoreCase("F")) {
                                                error = true;
                                            }
                                            for (int i = 0; i < points.size(); i++) {
                                                if (points.get(i).getId().equals(startPointInput.getText())) {
                                                    start = points.get(i);
                                                    if (genderInput.getText().equalsIgnoreCase("M")
                                                            || genderInput.getText().equalsIgnoreCase("Male")) {
                                                        start.setMaleKangaroo(start.getMaleKangaroo() + 1);

                                                    } else if (genderInput.getText().equalsIgnoreCase("F")
                                                            || genderInput.getText().equalsIgnoreCase("Female")) {
                                                        start.setFemaleKangaroo(start.getFemaleKangaroo() + 1);
                                                    }
                                                    break;
                                                } else if (i == points.size() - 1 && !points.get(i).getId().equals(startPointInput.getText())) {
                                                    error = true;
                                                }
                                            }
                                            if (start == null) {
                                                Alert invalid = new Alert(Alert.AlertType.WARNING);
                                                invalid.setTitle("Invalid Input");
                                                invalid.setHeaderText("Invalid Input");
                                                invalid.showAndWait();
                                                return;
                                            }
                                            if (start.getCurrentKangarooNumber() > start.getSize()) {
                                                //Check whether the number of kangaroo on each point exceed the maximum or not                                                
                                                Alert exceed = new Alert(Alert.AlertType.WARNING);
                                                exceed.setTitle("Error");
                                                exceed.setHeaderText("Exceeded Maximum number of kangaroo");
                                                exceed.showAndWait();
                                                startPointInput.clear();
                                                noOfFoodInput.clear();
                                                genderInput.clear();
                                                return;
                                            }
                                            if (!error) {
                                                count++;
                                                kangarooList.add(new Kangaroo(start, Integer.parseInt(noOfFoodInput.getText()), genderInput.getText()));
                                            } else {
                                                Alert invalid = new Alert(Alert.AlertType.WARNING);
                                                invalid.setTitle("Invalid Input");
                                                invalid.setHeaderText("Invalid Input");
                                                invalid.showAndWait();
                                            }
                                            startPointInput.clear();
                                            noOfFoodInput.clear();
                                            genderInput.clear();
                                            System.out.println("MAXKANGAROO = " + MAXKANGAROO);
                                            if (count == MAXKANGAROO) {
                                                startPointInput.setEditable(false);
                                                noOfFoodInput.setEditable(false);
                                                genderInput.setEditable(false);
                                            }
                                        }
                                    });

                                    Stage kangarooInputStage = new Stage();
                                    HBox kangarooHBox = new HBox();
                                    final VBox kangarooVb = new VBox();
                                    kangarooTable.setItems(kangarooList);
                                    kangarooTable.getColumns().addAll(startPoint, gender, noOfFood);
                                    kangarooHBox.getChildren().addAll(startPointInput, genderInput, noOfFoodInput, addInput, ok);
                                    kangarooVb.setPadding(new Insets(0, 0, 0, 0));
                                    kangarooVb.setSpacing(5);
                                    kangarooVb.getChildren().addAll(kangarooTable, kangarooHBox);
                                    Scene kangarooScene = new Scene(new Group());
                                    ((Group) kangarooScene.getRoot()).getChildren().addAll(kangarooVb);
                                    kangarooInputStage.setScene(kangarooScene);
                                    kangarooInputStage.show();
                                }
                            });

                            addpath.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent e) {
                                    if (obstacleheightinput.getText().equals("")
                                            || ptconnected.getText().equals("")
                                            || ptid.getText().equals("")) {
                                        Alert incomplete = new Alert(Alert.AlertType.WARNING);
                                        incomplete.setTitle("Error");
                                        incomplete.setHeaderText("Incomplete data");
                                        incomplete.showAndWait();
                                        return;
                                    }

                                    if (entry < points.get(current).getPath()) {
                                        //Check whether the entries for the link exceed number of paths

                                        String obstacleheight = obstacleheightinput.getText();
                                        String destination = ptconnected.getText();
                                        String pathtable_pathid = ptid.getText();

                                        Point destinationPoint = null;
                                        PointPath path1 = null;
                                        //Path1 for connect A to B
                                        for (int i = 0, a = points.size(); i < a; i++) {
                                            destinationPoint = points.get(i);
                                            if (points.get(i).getPathid().contains(pathtable_pathid)) {
                                                //Check same path ID, if same ID, error
                                                Alert redundant = new Alert(Alert.AlertType.WARNING);
                                                redundant.setTitle("Error");
                                                redundant.setHeaderText("Redundant Path ID");
                                                redundant.showAndWait();
                                                obstacleheightinput.clear();
                                                ptconnected.clear();
                                                ptid.clear();
                                                return;
                                            }
                                            if (points.get(i).getId().equals(destination)) {
                                                //set the link
                                                path1 = new PointPath(sourceidinput.getText(), destinationPoint, pathtable_pathid,
                                                        Integer.parseInt(obstacleheight));
                                                break;
                                            }
                                            if (i == points.size() - 1 && !points.get(i).getId().equals(destination)) {
                                                //Check until the last index of list and if the list does not contains the destination point
                                                //display error
                                                Alert xexist = new Alert(Alert.AlertType.WARNING);
                                                xexist.setTitle("Error");
                                                xexist.setHeaderText("Point does not exists");
                                                xexist.showAndWait();
                                                ptconnected.clear();
                                                ptid.clear();
                                                obstacleheightinput.clear();
                                                return;
                                            }

                                        }

                                        boolean repeat = false;
                                        if (points.get(current).getId().equals(path1.getP().getId())) {
                                            repeat = true;
                                        }
                                        for (int i = 0; i < points.get(current).getLink().size(); i++) {
                                            if (points.get(current).getLink().get(i).equals(path1.getP())) {
                                                repeat = true;
                                                break;
                                            }
                                        }
                                        for (int i = 0; i < pathTableList.size(); i++) {
                                            if (pathTableList.get(i).getPathid().equals(pathtable_pathid)) {
                                                repeat = true;
                                                break;
                                            }
                                        }
                                        if (repeat) {
                                            Alert repeatpath = new Alert(Alert.AlertType.WARNING);
                                            repeatpath.setTitle("Repeated Path");
                                            repeatpath.setHeaderText("Repeated Path");
                                            repeatpath.showAndWait();
                                            ptconnected.clear();
                                            ptid.clear();
                                            obstacleheightinput.clear();
                                            return;
                                        } else {
                                            points.get(current).setLink(destinationPoint, pathtable_pathid);
                                        }
                                        ptconnected.clear();
                                        ptid.clear();
                                        obstacleheightinput.clear();
                                        pathTableList.add(path1);
                                        pathList.add(destinationPoint);
                                        entry++;
                                        if (entry == points.get(current).getPath()) {
                                            current++;
                                            entry = 0;
                                            if (current < points.size()) {
                                                //move to next point after filling the current point's path
                                                sourceidinput.clear();
                                                while (points.get(current).getPath() == 0) {
                                                    current++;
                                                    if (current == points.size()) {
                                                        //if last path is 0 then end
                                                        return;
                                                    }
                                                }
                                                sourceidinput.setText(points.get(current).getId());

                                            } else {
                                                //all paths have been filled
                                                ptconnected.setEditable(false);
                                                ptid.setEditable(false);
                                                obstacleheightinput.setEditable(false);
                                                sourceidinput.clear();
                                            }
                                        }

                                    } else {
                                        if (entry == points.get(current).getPath()) {
                                            //Reached the maximum of the current point's number of path
                                            current++;
                                            //move to next point
                                            sourceidinput.clear();
                                            sourceidinput.setText(points.get(current).getId());
                                            entry = 0;
                                        } else {
                                            Alert al = new Alert(Alert.AlertType.WARNING);
                                            al.setTitle("Error");
                                            al.setHeaderText("Path exceeded");
                                            al.show();
                                        }
                                    }

                                }
                            });

                            pathTable.setItems(pathTableList);
                            pathTable.getColumns().addAll(sourceid, link, pathid, obstacleheight);

                            pathhb.getChildren().addAll(sourceidinput, ptconnected, ptid,
                                    obstacleheightinput, addpath, ok);
                            pathhb.setSpacing(5);

                            final VBox pathvb = new VBox();
                            pathvb.setSpacing(5);
                            pathvb.setPadding(new Insets(0, 0, 0, 0));
                            pathvb.getChildren().addAll(pathTable, pathhb);

                            Scene pathscene = new Scene(new Group());
                            ((Group) pathscene.getRoot()).getChildren().addAll(pathvb);

                            pathstage.setScene(pathscene);
//                            pathstage.setResizable(false);
                            pathstage.show();

                            pathstage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent t) {
                                    pathTableList.clear();
                                    pathList.clear();
                                    pathstage.close();

                                }
                            });
                        }
                    }
                });
                table.setItems(points);
                table.getColumns().addAll(id, food, size, path);
                table.setMinWidth(650);
                table.setMaxWidth(650);

                final VBox vbox = new VBox();
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(0, 0, 0, 0));
                vbox.getChildren().addAll(table, hb);
                Scene scene = new Scene(new Group());
                ((Group) scene.getRoot()).getChildren().addAll(vbox);

                stage.setTitle("Point(s) Data");
                stage.setScene(scene);
                stage.show();

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent t) {
                        points.clear();
                        stage.close();
                    }
                });
            }
        });
        btn.setLayoutX(125);
        btn.setLayoutY(125);
        Pane root = new Pane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);
        try {
            Image kangaroo1 = new Image(new FileInputStream("Kangaroo-Left.gif"));
            ImageView imageView = new ImageView(kangaroo1);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setX(150);
            imageView.setY(15);
            imageView.setPreserveRatio(true);
            Path path = new Path();
//            path.getElements().add(new MoveTo(0, 15));
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.seconds(3));
            pathTransition.setPath(new Line(300, 55, 0, 55));
            pathTransition.setNode(imageView);
//            pathTransition.setAutoReverse(true);
//            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setCycleCount(Timeline.INDEFINITE);
            pathTransition.play();
            root.getChildren().add(imageView);

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }

        primaryStage.setTitle("Kangaroo");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);

    }

    public static void start() {

        point = new Point2D[points.size() + 1][points.size() + 1];
        LinkedList<Circle> circle = new LinkedList<>();
        Random r = new Random();
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        ArrayList<Color> color = new ArrayList<>();
        ArrayList<Integer[]> lineUsed = new ArrayList<>();

        int[][] pointsCoord = new int[20][2];
        ArrayList<Integer> usedCoord = new ArrayList<>();
        color.add(Color.BLUEVIOLET);
        color.add(Color.SALMON);
        color.add(Color.MEDIUMTURQUOISE);
        color.add(Color.FORESTGREEN);
        color.add(Color.MISTYROSE);
        color.add(Color.RED);
        color.add(Color.LIGHTCORAL);
        color.add(Color.LIGHTSLATEGREY);
        color.add(Color.MEDIUMSLATEBLUE);
        ArrayList<Line> line = new ArrayList<>();
        ArrayList<Pane> figure = new ArrayList<>();
        ArrayList<Text> pathID = new ArrayList<>();
        ArrayList<Integer> leftOrRight = new ArrayList<>();
        //To determine the points is at left of scene or right
        int radius = 50;

        //Assigning coordinates for points into array using ellipse equation
        for (int i = 0, tempX = 200, tempY = 500; i < pointsCoord.length; i++) {
            pointsCoord[i][0] = tempX;
            pointsCoord[i][1] = tempY;
            if (i < 10) {
                tempX += 160;
                double a = 1 - (Math.pow(tempX - 1000, 2) / Math.pow(800, 2));
                double b = Math.pow(400, 2);
                tempY = (int) (Math.sqrt(a * b)) + 500;

            } else {
                tempX -= 160;
                double a = 1 - (Math.pow(tempX - 1000, 2) / Math.pow(800, 2));
                double b = Math.pow(400, 2);
                tempY = (int) (Math.sqrt(a * b)) + 500;
                tempY = 1000 - tempY;
            }

        }

        for (int i = 0; i < points.size(); i++) {
            //Assigning coordinates to each points with randomly selected coordinates from array.
            int n = r.nextInt(20);
            while (usedCoord.contains(n)) {
                n = r.nextInt(20);
            }
            usedCoord.add(n);
            x.add(pointsCoord[n][0]);
            y.add(pointsCoord[n][1]);
        }

        Collections.shuffle(color);
        for (int i = 0; i < points.size(); i++) {
            Circle c = new Circle();
            Rectangle rt = new Rectangle();
            Pane sp = new Pane();
            Text t = new Text();
            t.setX(x.get(i) - 5);
            t.setY(y.get(i) + 5);
            t.setText(points.get(i).getId());
            rt.setX(x.get(i) - 20);
            rt.setY(y.get(i) - 20);
            rt.setVisible(true);
            rt.setStrokeWidth(10);
            rt.setWidth(radius - 10);
            rt.setHeight(radius - 10);
            rt.setFill(Color.ORANGE);
            c.setVisible(true);
            c.setRadius(radius);
            c.setFill(color.get(i % color.size()));
            points.get(i).setX(x.get(i));
            points.get(i).setY(y.get(i));
            c.setCenterX(x.get(i));
            c.setCenterY(y.get(i));
            c.setId(points.get(i).getId());
            sp.getChildren().addAll(c);
            sp.getChildren().addAll(rt);
            sp.getChildren().addAll(t);
            figure.add(sp);
            circle.add(c);
        }

        for (int i = 0; i < pathTableList.size(); i++) {
            int startX, startY, endX, endY;
            Line l = new Line();
            boolean sameLine = false;
            Point source = null;
            for (int j = 0; j < points.size(); j++) {
                if (pathTableList.get(i).getSource().equals(points.get(j).getId())) {
                    source = points.get(j);
                    break;
                }
            }
            startX = source.getX();
            startY = source.getY();
            endX = pathTableList.get(i).getP().getX();
            endY = pathTableList.get(i).getP().getY();
            for (int k = 0; k < lineUsed.size(); k++) {
//                System.out.println("Array: " + Arrays.toString(lineUsed.get(k)));
//                System.out.println("Points: " + startX + " " + startY + " " + endX + " " + endY);
                if (lineUsed.get(k)[0] == endX && lineUsed.get(k)[1] == endY
                        && lineUsed.get(k)[2] == startX && lineUsed.get(k)[3] == startY) {
                    sameLine = true;
                    break;
                }
            }
            if (sameLine) {
                startX -= 5;
                startY -= 5;
                endX -= 15;
                endY -= 15;
            } else {
                Integer[] temp = new Integer[4];
                temp[0] = startX;
                temp[1] = startY;
                temp[2] = endX;
                temp[3] = endY;
                lineUsed.add(temp);
                startX += 5;
                startY += 5;
                endX += 15;
                endY += 15;
            }

            l.setStartX(startX);
            l.setStartY(startY);
            l.setEndX(endX);
            l.setEndY(endY);
            l.setVisible(true);
            line.add(l);

            double midPointX = (l.getStartX() + l.getEndX()) / 2 - 5;
            double midPointY = (l.getStartY() + l.getEndY()) / 2 + 5;
            Text id = new Text(pathTableList.get(i).getPathid());
            id.setX(midPointX);
            id.setY(midPointY);
            id.setFont(Font.font("Verdana", 20));
            id.setFill(Color.RED);
            pathID.add(id);
        }
        for (int i = 0; i < kangarooList.size(); i++) {
            if (kangarooList.get(i).getStartPoint().getX() <= 1000) {
                leftOrRight.add(0);
            } else {
                leftOrRight.add(1);
            }
        }
        System.out.println("left or right : " + leftOrRight.toString());

//        Path path = new Path();
//        PathTransition pt = new PathTransition();
//        path.getElements().add(new MoveTo(0, 15));
//        pt.setDuration(Duration.seconds(3));
//        pt.setPath(new Line(300, 55, 0, 55));
//        pt.setNode(iv[0]);
//        pt.setCycleCount(Timeline.INDEFINITE);
//        pt.play();
        Pane pane = new Pane();
        pane.getChildren().addAll(line);
        pane.getChildren().addAll(figure);
        pane.getChildren().addAll(pathID);

        Stage stage = new Stage();
        stage.setMaximized(true);

        stage.setTitle("Kangaroo");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        //Start run
        //Timer for food regenerative
        int delay = 1000;
        int period = 1000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                for (int i = 0; i < points.size(); i++) {
                    Point p = points.get(i);
                    p.setFood(p.getFood() + 1);
                    System.out.println("Point: " + p.getId() + " current food: " + p.getFood());

                }
                if (endRun) {
                    timer.cancel();
                }
            }
        }, delay, period);

        //Kangaroo take food from starting point and keep in pouch
        for (int i = 0; i < kangarooList.size(); i++) {
            Kangaroo k = kangarooList.get(i);
            if (k.getStartPoint().getFood() == 0) {
                continue;
            }
            if (k.getStartPoint().getFood() > k.getMaxPouchFood()) {
                k.setNoOfFood(k.getMaxPouchFood());
                k.getStartPoint().setFood(k.getStartPoint().getFood() - k.getMaxPouchFood());
            } else {
                k.setNoOfFood(k.getStartPoint().getFood());
                k.getStartPoint().setFood(0);
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
        }

        Image image[] = new Image[2];
        try {
            image[0] = new Image(new FileInputStream("Kangaroo-Left.gif"));
            image[1] = new Image(new FileInputStream("Kangaroo-Right.gif"));
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        ImageView iv[] = new ImageView[2];
        iv[0] = new ImageView(image[0]);
        iv[0].setFitWidth(250);
        iv[0].setFitHeight(250);
        iv[1] = new ImageView(image[1]);
        iv[1].setFitWidth(250);
        iv[1].setFitHeight(250);
        ArrayList<PathTransition> pathTransition = new ArrayList<>();
        ArrayList<ImageView> kangarooImage = new ArrayList<>();
//        for (int i = 0; i < kangarooList.size(); i++) {
//            pathTransition.add(new PathTransition());
//            if (leftOrRight.get(i) == 0) {
//                kangarooImage.add(iv[0]);
//            } else {
//                kangarooImage.add(iv[1]);
//            }
//        }
        for (int i = 0; i < kangarooList.size(); i++) {
            pathTransition.add(new PathTransition());
        }
        ArrayList<Integer[]> usedCoords = new ArrayList<>();
        while (true) {

            //Determine is there any possible moves there
            boolean isMoveable = false;
            for (int i = 0; i < kangarooList.size(); i++) {
                Kangaroo k = kangarooList.get(i);

//                System.out.println("Kangaroo " + i + " gender is " + k.getGender());
                Path p;
                startPathX = k.getCurrentPoint().getX();
                startPathY = k.getCurrentPoint().getY();
                String startPoint = k.getCurrentPoint().getId();
                boolean kangarooMoves = move(k);
                ImageView view;
                if (leftOrRight.get(i) == 0) {
                    view = new ImageView(image[0]);
                    view.setFitWidth(250);
                    view.setFitHeight(250);
                } else {
                    view = new ImageView(image[1]);
                    view.setFitWidth(250);
                    view.setFitHeight(250);
                }
                if (kangarooMoves) {

                    endPathX = k.getCurrentPoint().getX();
                    endPathY = k.getCurrentPoint().getY();
                    isMoveable = true;
                    p = new Path();
                    p.getElements().add(new MoveTo(startPathX, startPathY));
                    p.getElements().add(new LineTo(endPathX, endPathY));
                    System.out.println("Kangaroo " + i + " moves from " + startPoint
                            + " to " + k.getCurrentPoint().getId());
                    pathTransition.get(i).setNode(view);
                    pathTransition.get(i).setPath(p);
                    pathTransition.get(i).setDuration(Duration.seconds(5));
                    pathTransition.get(i).setCycleCount(1);
                    pane.getChildren().add(view);
                } else {
                    endPathX = k.getCurrentPoint().getX();
                    endPathY = k.getCurrentPoint().getY();
                    p = new Path();
//                    Integer[] coords = new Integer[2];
//                    Random random = new Random();
//                    coords[0] = k.getCurrentPoint().getX();
//                    coords[1] = k.getCurrentPoint().getY();
//                    if (usedCoords.contains(coords)) {
//                        coords[0] -= random.nextInt(20);
//                        coords[1] -= random.nextInt(20);
//                    }
//                    usedCoords.add(coords);
                    p.getElements().add(new MoveTo(startPathX, startPathY));
                    p.getElements().add(new LineTo(endPathX, endPathY));
                    pathTransition.get(i).setNode(view);
                    pathTransition.get(i).setPath(p);
                    pathTransition.get(i).setDuration(Duration.seconds(4));
                    pathTransition.get(i).setCycleCount(Timeline.INDEFINITE);
                    pane.getChildren().add(view);
                }
                pathTransition.get(i).play();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            //If no moves then output result
            if (!isMoveable) {
                System.out.println("Number of colonies: " + sumOfColony);
                for (int i = 0; i < kangarooList.size(); i++) {
                    Kangaroo k = kangarooList.get(i);
                    System.out.println("Kangaroo test: " + k.getCurrentPoint().toString2()
                            + " Gender: " + k.getGender() + " Food in pouch: " + k.getNoOfFood());
                }
                endRun = true;
                break;
            }

        }

    }

//    public static PathTransition createPathTransition(Path p, Node n) {
//        PathTransition pt = new PathTransition();
//        
//
//    }
    public static boolean move(Kangaroo k) {
        //Check for availability of food on map
        boolean isFoodAvailableOnMap = false;
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getFood() > 0) {
                isFoodAvailableOnMap = true;
                break;
            }
        }
//        startPathX = k.getCurrentPoint().getX();
//        startPathY = k.getCurrentPoint().getY();
//        System.out.println("isFoodAvailableOnMap: "+isFoodAvailableOnMap);
        if (k.getGender().equalsIgnoreCase("F") || k.getGender().equalsIgnoreCase("Female")) {
            return false;
        }

        //Male
        if (k.getGender().equalsIgnoreCase("M") || k.getGender().equalsIgnoreCase("Male")) {
            //Check whether the kangaroo already in a colony            
            if (!k.isColonised()) {
                double mostFoodLeft = -999;
                int mostFemaleKangaroo = -1;
                boolean moved = false;
                boolean equalMostFood = false;
                double foodNeededToJump = 0;
                ArrayList<Integer> tempCounter = new ArrayList<>();
                int tempI = -1;
                startPathX = k.getCurrentPoint().getX();
                startPathY = k.getCurrentPoint().getY();
                //Check for which point will result in most food after travelling
                for (int i = 0; i < pathTableList.size(); i++) {
                    if (pathTableList.get(i).getSource().equals(k.getCurrentPoint().getId())) {
                        //Check current point where the kangaroo at
                        if (pathTableList.get(i).getP().getCurrentKangarooNumber()
                                < pathTableList.get(i).getP().getSize()) {
                            //Check current kangaroo number at destination, whether full or not
                            foodNeededToJump = pathTableList.get(i).getObstacleheight() + (0.5 * k.getNoOfFood());
                            if (isFoodAvailableOnMap) {
                                if (pathTableList.get(i).getP().getFood() > 0) {
                                    //Check whether the food is enough to jump there or not
                                    if ((k.getNoOfFood() + pathTableList.get(i).getP().getFood()) >= foodNeededToJump) {
                                        //Check kangaroo's current food and the destination food
                                        //If greater than the food needed, then can jump
                                        //Assume the kangaroo can take the destination food even he havent jump
                                        double foodLeftOnPoint = pathTableList.get(i).getP().getFood() - foodNeededToJump;

                                        //Testing for colony when map still got food
                                        //Seems ok
                                        boolean isAbleToJump = true;
                                        boolean isPointColonised = pathTableList.get(i).getP().isIsColonised();
                                        if (isPointColonised) {
                                            isAbleToJump = k.getNoOfFood() + foodLeftOnPoint >= pathTableList.get(i).getP().getCurrentKangarooNumber();
                                        }
                                        //

                                        //Check for most foodLeft
                                        if (foodLeftOnPoint > mostFoodLeft && isAbleToJump) {
                                            mostFoodLeft = foodLeftOnPoint;
                                            equalMostFood = false;
                                            moved = true;
                                            tempI = i;
                                            //tempI is the point with highest food
                                            tempCounter = new ArrayList();
                                            //refresh the list if found another higher food point
                                            tempCounter.add(tempI);
                                        } else if (foodLeftOnPoint == mostFoodLeft && isAbleToJump) {
                                            //When have multiple points with the same foodLeft
                                            moved = true;
                                            equalMostFood = true;
                                            tempCounter.add(i);
                                        }
                                    }
                                }
                            } else {
                                /**
                                 * When all points have no food left, movement
                                 * determined by female kangaroo in adjacent
                                 * points and food in pouch enough or not
                                 */

                                int female = pathTableList.get(i).getP().getFemaleKangaroo();
                                boolean isPointColonised = pathTableList.get(i).getP().isIsColonised();
                                boolean isAbleToJump;
                                //Determine if the point is already colonised or not
                                if (isPointColonised) {
                                    isAbleToJump = k.getNoOfFood() - foodNeededToJump >= pathTableList.get(i)
                                            .getP().getCurrentKangarooNumber();
                                    //if the point is colonised, the newcomer kangaroo need to distribute 1 of his food
                                    //to all of the kangaroo at the point
                                    //Jh: if colonised, the distribution doesn't matter d right, because it only need to know whether he has enough for
                                    //all but they share the food d, so no need implement the distribution.
                                } else {
                                    isAbleToJump = k.getNoOfFood() >= foodNeededToJump;
                                    //if not colonised, if the kangaroo has more or equal food
                                    //then the kangaroo can jump
                                }
                                if (female > mostFemaleKangaroo && isAbleToJump) {
                                    mostFemaleKangaroo = female;
                                    moved = true;
                                    tempI = i;
                                    //To be deducted from the kangaroo pouch at (moved) condition
                                    mostFoodLeft = -foodNeededToJump;

                                }
                            }
                        }
                    }
                }

                if (moved) {
                    //If have points with equal amount of food
                    if (equalMostFood) {
                        //Check for which points will result in most females
                        int mostFemale = -999;
                        tempI = tempCounter.get(0);
                        for (int i = 0; i < tempCounter.size(); i++) {
                            PointPath p = pathTableList.get(tempCounter.get(i));
                            if (p.getP().getFemaleKangaroo() > mostFemale) {
                                mostFemale = p.getP().getFemaleKangaroo();
                                tempI = tempCounter.get(i);
                                //tempI is the index of the point with highest number of female
                            }
                        }
                    }

                    //Moving the kangaroo to new point and update all the variables
                    Point p = pathTableList.get(tempI).getP();

                    //Remove the kangaroo from original point, not sure will work or not                
                    k.getCurrentPoint().setMaleKangaroo(k.getCurrentPoint().getMaleKangaroo() - 1);
                    k.setCurrentPoint(p);
                    p.setMaleKangaroo(p.getMaleKangaroo() + 1);
//                    p.setSize(p.getSize() - 1);

                    //There are no more food on the map
//                    if (!isFoodAvailableOnMap) {
                    //The point does not have a colony yet
                    if (!p.isIsColonised()) {
//                            System.out.println(p.toString2()+ " kangaroo count: "+p.getCurrentKangarooNumber());
                        if (p.getCurrentKangarooNumber() == colonyThreshold) {
                            p.setIsColonised(true);
                            sumOfColony++;
                            k.setColony(p);
                            for (int i = 0; i < kangarooList.size(); i++) {
                                Kangaroo tempK = kangarooList.get(i);
                                if (tempK.getCurrentPoint().equals(p)) {
                                    tempK.setColonised(true);
                                    tempK.setColony(p);
                                }
                            }
                        }
                    } else { //The point already have colony
                        //Set kangaroo to the colony point and set to colonised
                        k.setColony(p);
                        k.setColonised(true);
                    }
//                    }

                    int foodAvailable = (int) mostFoodLeft;
                    if (foodAvailable < 0) {
                        //If foodAvailable < 0, means kangaroo need to use food from its pouch
                        k.setNoOfFood(k.getNoOfFood() + foodAvailable);
                        p.setFood(0);

                    } else if (foodAvailable < k.getMaxPouchFood()) {
                        //to calculate the food needed to get back to max no of food
                        int foodAdded = k.getMaxPouchFood() - k.getNoOfFood();
                        foodAvailable = foodAvailable - foodAdded;
                        //add kangaroo's current food with the food available in the point
                        k.setNoOfFood(foodAdded + k.getNoOfFood());
                        //the food left in the point after kangaroo take
                        p.setFood(foodAvailable);
                    } else {
                        k.setNoOfFood(foodAvailable + k.getNoOfFood());
                        p.setFood(foodAvailable - k.getNoOfFood());
                    }
//                    System.out.println("Kangaroo at "+k.getCurrentPoint().toString2());
//                    System.out.println("Kangaroo Food: "+k.getNoOfFood());
//                    System.out.println("Point food: "+k.getCurrentPoint().getFood());
//                    endPathX = k.getCurrentPoint().getX();
//                    endPathY = k.getCurrentPoint().getY();

                    return true;
                } else {
//                    endPathX = startPathX;
//                    endPathY = startPathY;
                    return false;
                    //To be determined what to do
                }
            } else {
//                endPathX = startPathX;
//                endPathY = startPathY;
                return false;
            }

        } else {
//            endPathX = startPathX;
//            endPathY = startPathY;
            return false;
        }
    }
}
