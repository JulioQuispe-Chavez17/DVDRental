package controller;

import dao.DashboardImpl;
import lombok.Getter;
import lombok.Setter;
import model.Dashboard;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.polar.PolarAreaChartDataSet;
import org.primefaces.model.charts.polar.PolarAreaChartModel;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.polar.PolarAreaChartOptions;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class DashboardTwoC implements Serializable {
    private @Getter
    @Setter
    DonutChartModel donutModel;
    private @Getter
    @Setter
    PolarAreaChartModel polarAreaModel;
    private @Getter
    @Setter
    DashboardImpl dao;
    private @Getter
    @Setter
    List<Dashboard>  listaDonut, listaPolarArea;

    public DashboardTwoC() {
        dao = new DashboardImpl();
        listaDonut = new ArrayList<>();
        listaPolarArea = new ArrayList<>();
    }

    @PostConstruct
    public void init() throws Exception {
        createDonutModel();
        createPolarAreaModel();
    }
    public void createDonutModel() throws Exception {
        listaDonut = dao.topPaisesVentas();
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        for (Dashboard modelo: listaDonut) {
            values.add(modelo.getTotal());

        }
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("RGB(205, 92, 92)");
        bgColors.add("rgb(127, 255, 0)");
        bgColors.add("rgb(255, 127, 80)");
        bgColors.add("rgb(255, 140, 0)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        for (Dashboard modelo: listaDonut) {
            labels.add(modelo.getCountry());

        }
        DonutChartOptions options = new DonutChartOptions();

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Top 10 Países con más ventas");
        options.setTitle(title);



        Legend legend = new Legend();
        legend.setPosition("top");
        options.setLegend(legend);

        data.setLabels(labels);
        donutModel.setData(data);
        donutModel.setOptions(options);
    }

    private void createPolarAreaModel() {
        listaPolarArea = dao.topPaisesClientes();
        polarAreaModel = new PolarAreaChartModel();
        ChartData data = new ChartData();

        PolarAreaChartDataSet dataSet = new PolarAreaChartDataSet();
        List<Number> values = new ArrayList<>();
        for (Dashboard modelo: listaPolarArea) {
            values.add(modelo.getTotal());

        }
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(201, 203, 207)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        for (Dashboard modelo: listaPolarArea) {
            labels.add(modelo.getCountry());

        }
        PolarAreaChartOptions options = new PolarAreaChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Top 10 Países con más clientes");

        options.setTitle(title);

        data.setLabels(labels);

        polarAreaModel.setData(data);
        polarAreaModel.setOptions(options);
    }

}
