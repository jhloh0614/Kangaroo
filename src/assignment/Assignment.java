package assignment;

import java.util.LinkedList;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Assignment extends Application {

    public Point2D point[][];
    public int ptno;
    private TableView table = new TableView();
    ObservableList<Point> points = FXCollections.observableArrayList();
    private boolean donesetpoint;
    private TableView pathtable = new TableView();
    ObservableList<Point> pathlist = FXCollections.observableArrayList();
    ObservableList<Path> pathtablelist = FXCollections.observableArrayList();
    private int rowno;

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
                food.setCellValueFactory(new PropertyValueFactory<Point, Integer>("food"));
                size.setCellValueFactory(new PropertyValueFactory<Point, String>("size"));
                path.setCellValueFactory(new PropertyValueFactory<Point, String>("path"));
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
//                        new EventHandler<CellEditEvent<Point, Integer>>() {
//                    @Override
//                    public void handle(CellEditEvent<Point, Integer> t) {
//                        Integer val = t.getNewValue();
//                        ((Point) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())).setFood(t.getNewValue());
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
                final int ptnoconstant = ptno;

                add.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
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
                        if (rowno == ptnoconstant) {
                            Stage pathstage = new Stage();
                            HBox pathhb = new HBox();
                            TableColumn sourceid = new TableColumn("Source ID");
                            TableColumn link = new TableColumn("Linked to");
                            TableColumn obstacleHeight = new TableColumn("Obstacle Height");
                            TableColumn pathid = new TableColumn("Path ID");

//                            sourceid.setCellValueFactory(new PropertyValueFactory<Point, String>("id"));
//                            link.setCellValueFactory(new PropertyValueFactory<Point, LinkedList<Point>>("link"));
//                            pathid.setCellValueFactory(new PropertyValueFactory<Point, LinkedList<String>>("pathid"));
                            sourceid.setCellValueFactory(new PropertyValueFactory<Path, String>("source"));
                            link.setCellValueFactory(new PropertyValueFactory<Path, Point>("p"));
                            obstacleHeight.setCellValueFactory(new PropertyValueFactory<Path, String>("obstacleHeight"));
                            pathid.setCellValueFactory(new PropertyValueFactory<Path, String>("pathid"));
                            sourceid.setMinWidth(150);
                            link.setMinWidth(150);
                            obstacleHeight.setMinWidth(100);
                            pathid.setMinWidth(100);
                            pathtable.setEditable(true);

                            TextField sourceidinput = new TextField();
                            TextField ptconnected = new TextField();
                            TextField heightInput = new TextField();
                            TextField ptid = new TextField();
                            ptconnected.setPromptText("Point Connected");
                            ptid.setPromptText("Path ID");
                            heightInput.setPromptText("Obstacle Height");
                            Button addpath = new Button("Add path");
                            sourceidinput.setText(points.get(index).getId());
                            sourceidinput.setEditable(false);
                            addpath.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    if (entry < points.get(index).getPath()) {
                                        String source = sourceidinput.getText();
                                        String destination = ptconnected.getText();
                                        String heightOutput = heightInput.getText();
                                        String pathtable_pathid = ptid.getText();

                                        Point p = null;
                                        Path path = null;

                                        for (int i = 0, a = points.size(); i < a; i++) {

                                            if (points.get(i).getPathid().contains(pathtable_pathid)) {
                                                Alert redundant = new Alert(Alert.AlertType.WARNING);
                                                redundant.setTitle("Error");
                                                redundant.setHeaderText("Redundant Path ID");
                                                redundant.showAndWait();
                                                ptconnected.clear();
                                                ptid.clear();
                                                return;
                                            }
                                            if (points.get(i).getId().equals(destination)) {
                                                p = points.get(i);
                                                path = new Path(sourceidinput.getText(), points.get(i), heightOutput, pathtable_pathid);
                                                break;
                                            }
                                            if (i == points.size() - 1 && !points.get(i).getId().equals(destination)) {
                                                Alert xexist = new Alert(Alert.AlertType.WARNING);
                                                xexist.setTitle("Error");
                                                xexist.setHeaderText("Point does not exists");
                                                xexist.showAndWait();
                                                ptconnected.clear();
                                                ptid.clear();
                                                return;
                                            }

                                        }

                                        pathtablelist.add(path);
                                        pathlist.add(p);
                                        entry++;
                                        System.out.println(pathtablelist.toString());
//                                        pathlist.add(new Point(source, destination, pathtable_pathid));
                                        points.get(index).setLink(p, pathtable_pathid);
                                        ptconnected.clear();
                                        heightInput.clear();
                                        ptid.clear();
                                        if (entry == points.get(index).getPath()) {
                                            index++;
                                            sourceidinput.clear();
                                            //Source id bug
                                            sourceidinput.setText(points.get(index).getId());
                                            entry = 0;
                                        }

                                    } else {
                                        if (entry == points.get(index).getPath()) {
                                            index++;
                                            System.out.println(index);
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
                            pathtable.getColumns().addAll(sourceid, link, obstacleHeight, pathid);

                            pathhb.getChildren().addAll(sourceidinput, ptconnected, heightInput, ptid, addpath);
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

}
