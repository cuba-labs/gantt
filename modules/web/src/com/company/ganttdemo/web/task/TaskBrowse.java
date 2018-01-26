package com.company.ganttdemo.web.task;

import com.company.ganttdemo.entity.Task;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import com.vaadin.ui.Layout;
import org.tltv.gantt.Gantt;
import org.tltv.gantt.client.shared.Step;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskBrowse extends AbstractLookup {
    @Inject
    private HBoxLayout ganttBox;
    @Inject
    private HierarchicalDatasource<Task, UUID> tasksDs;

    private Gantt gantt;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init(Map<String, Object> params) {
        initGantt();

        tasksDs.addCollectionChangeListener(e ->
                updateGanttData());
    }

    private void updateGanttData() {
        gantt.removeSteps();
        addSteps(tasksDs.getRootItemIds(), null);
    }

    private void initGantt() {
        gantt = new Gantt();
        gantt.setSizeFull();

        // Demo range
        try {
            gantt.setStartDate(dateFormat.parse("2018-01-01"));
            gantt.setEndDate(dateFormat.parse("2018-03-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ganttBox.unwrap(Layout.class).addComponent(gantt);
    }

    private void addSteps(Collection<UUID> ids, Step predecessor) {
        for (UUID id : ids) {
            Step step = createStep(tasksDs.getItemNN(id), predecessor);
            gantt.addStep(step);

            addSteps(tasksDs.getChildren(id), step);
        }
    }

    private Step createStep(Task task, @Nullable Step predecessor) {
        Step step = new Step();

        step.setCaption(task.getName());
        // For some reason description can't be null
        step.setDescription(task.getDescription() != null ? task.getDescription() : "");
        step.setStartDate(task.getStartDate());
        step.setEndDate(task.getEndDate());
        step.setPredecessor(predecessor);

        return step;
    }
}