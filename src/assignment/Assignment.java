package assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Assignment extends Application {

    private static Point2D point[][];
    private int ptno;
    private TableView table = new TableView();
    static ObservableList<Point> points = FXCollections.observableArrayList();
    private boolean donesetpoint;
    private TableView pathtable = new TableView();
    static ObservableList<Point> pathlist = FXCollections.observableArrayList();
    static ObservableList<Path> pathtablelist = FXCollections.observableArrayList();
    private int rowno;
    private int ptnoconstant;

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
                    ptno = Integer.parseInt(td1.getEditor().getText());
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
                id.setMaxWidth(50);
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
                ptnoconstant = ptno;

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

                        if (ptno < 1) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attention!");
                            alert.setHeaderText("Exceeded number of points");
                            alert.showAndWait();
                            return;
                        }
                        for (int i = 0, a = points.size(); i < a; i++) {
                            if (points.get(i).getId().equals(idinput.getText())) {
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
                        if (Integer.parseInt(pathinput.getText()) >= ptnoconstant) {
                            Alert exceeded = new Alert(Alert.AlertType.WARNING);
                            exceeded.setTitle("Error");
                            exceeded.setHeaderText("Number of path should be less than total number of points which is " + ptnoconstant);
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
                        ptno--;
                        rowno = id.getTableView().getItems().size();
                    }
                });
                Button ok = new Button("OK");
                hb.getChildren().addAll(idinput, foodinput, sizeinput,
                        pathinput, add, ok);
                hb.setSpacing(5);
                ok.setOnAction(new EventHandler<ActionEvent>() {

                    private int index = 0;
                    private int entry = 0;

                    @Override
                    public void handle(ActionEvent e) {
                        stage.close();
                        if (rowno == ptnoconstant) {
                            Stage pathstage = new Stage();
                            HBox pathhb = new HBox();
                            TableColumn sourceid = new TableColumn("Source ID");
                            TableColumn link = new TableColumn("Linked to");
                            TableColumn pathid = new TableColumn("Path ID");
                            TableColumn obstacleheight = new TableColumn("Obstacle Height");
//                            sourceid.setCellValueFactory(new PropertyValueFactory<Point, String>("id"));
//                            link.setCellValueFactory(new PropertyValueFactory<Point, LinkedList<Point>>("link"));
//                            pathid.setCellValueFactory(new PropertyValueFactory<Point, LinkedList<String>>("pathid"));
                            sourceid.setCellValueFactory(new PropertyValueFactory<Path, String>("source"));
                            link.setCellValueFactory(new PropertyValueFactory<Path, Point>("p"));
                            pathid.setCellValueFactory(new PropertyValueFactory<Path, String>("pathid"));
                            obstacleheight.setCellValueFactory(new PropertyValueFactory<Path, Integer>("obstacleheight"));

                            sourceid.setMinWidth(150);
                            link.setMinWidth(200);
                            pathid.setMinWidth(100);
                            obstacleheight.setMinWidth(200);

                            pathtable.setEditable(true);

                            TextField sourceidinput = new TextField();
                            TextField ptconnected = new TextField();
                            TextField ptid = new TextField();
                            TextField obstacleheightinput = new TextField();

                            ptconnected.setPromptText("Point Connected");
                            ptid.setPromptText("Path ID");
                            obstacleheightinput.setPromptText("Obstacle Height");

                            Button addpath = new Button("Add path");
                            sourceidinput.setText(points.get(index).getId());
                            sourceidinput.setEditable(false);

                            Button ok = new Button("OK");
                            ok.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent e) {
                                    pathstage.close();
                                    start();
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

                                    if (entry < points.get(index).getPath()) {
                                        String obstacleheight = obstacleheightinput.getText();
                                        String destination = ptconnected.getText();
                                        String pathtable_pathid = ptid.getText();

                                        Point p = null;
                                        Path path = null;
                                        for (int i = 0; i < pathtablelist.size(); i++) {
                                            if (pathtable_pathid.equals(pathtablelist.get(i).getPathid())) {
                                                Alert redundant = new Alert(Alert.AlertType.WARNING);
                                                redundant.setTitle("Error");
                                                redundant.setHeaderText("Redundant Path ID");
                                                redundant.showAndWait();
                                                obstacleheightinput.clear();
                                                ptconnected.clear();
                                                ptid.clear();
                                                return;
                                            }
                                        }
                                        for (int i = 0, a = points.size(); i < a; i++) {
                                            if (points.get(i).getPathid().contains(pathtable_pathid)) {
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
                                                p = points.get(i);
                                                path = new Path(sourceidinput.getText(), points.get(i), pathtable_pathid,
                                                        Integer.parseInt(obstacleheight));
                                                break;
                                            }
                                            if (i == points.size() - 1 && !points.get(i).getId().equals(destination)) {
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
                                        if (points.get(index).getId().equals(path.getP().getId())) {
                                            repeat = true;
                                        }
                                        for (int i = 0; i < points.get(index).getLink().size(); i++) {
                                            if (points.get(index).getLink().get(i).equals(path.getP())) {
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
                                            points.get(index).setLink(p, pathtable_pathid);
                                        }
                                        ptconnected.clear();
                                        ptid.clear();
                                        obstacleheightinput.clear();
                                        pathtablelist.add(path);
                                        pathlist.add(p);
                                        entry++;
                                        if (entry == points.get(index).getPath()) {
                                            index++;
                                            entry = 0;
                                            if (index < points.size()) {
                                                sourceidinput.clear();
                                                sourceidinput.setText(points.get(index).getId());

                                            } else {
                                                ptconnected.setEditable(false);
                                                ptid.setEditable(false);
                                                obstacleheightinput.setEditable(false);
                                                sourceidinput.clear();
                                            }
                                        }

                                    } else {
                                        if (entry == points.get(index).getPath()) {
                                            index++;
                                            sourceidinput.clear();
                                            sourceidinput.setText(points.get(index).getId());
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

                            pathtable.setItems(pathtablelist);
                            pathtable.getColumns().addAll(sourceid, link, pathid, obstacleheight);

                            pathhb.getChildren().addAll(sourceidinput, ptconnected, ptid,
                                    obstacleheightinput, addpath, ok);
                            pathhb.setSpacing(5);

                            final VBox pathvb = new VBox();
                            pathvb.setSpacing(5);
                            pathvb.setPadding(new Insets(0, 0, 0, 0));
                            pathvb.getChildren().addAll(pathtable, pathhb);

                            Scene pathscene = new Scene(new Group());
                            ((Group) pathscene.getRoot()).getChildren().addAll(pathvb);

                            pathstage.setScene(pathscene);
                            pathstage.setResizable(false);
                            pathstage.show();

                            pathstage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent t) {
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

                stage.setResizable(false);
                stage.setTitle("Point(s) Data");
                stage.setScene(scene);
                stage.show();

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent t) {
                        stage.close();
                    }
                });
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Kangaroo");
        primaryStage.setResizable(false);
//        primaryStage.getIcons().add(new Image("kangaroo-icon.png"));
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
        for (int i = 100; i <= 350; i++) {
            x.add(i);
            y.add(i);
        }
        Collections.shuffle(x);
        Collections.shuffle(y);
        Line l[] = new Line[pathtablelist.size()];
        for (int i = 0; i < points.size(); i++) {
            System.out.println("X: " + x.get(i));
            System.out.println("Y: " + y.get(i));
            Circle c = new Circle();
            c.setVisible(true);
            c.setRadius(20);
            c.setFill(Color.AQUA);
            c.setCenterX(x.get(i));
            c.setCenterY(y.get(i));
            circle.add(c);
        }
        Pane pane = new Pane();
        pane.getChildren().addAll(circle);
        Stage stage = new Stage();
        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

}
