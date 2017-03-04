/*
*
* Created May 10, 2016, 2:17:29 PM. 
* @author Stress <steelgseries@hotmail.com>
* Copyright (C) 2016  Stress

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
*
*/
package resources.tasks;

import org.osbot.rs07.script.Script;

import java.util.ArrayList;
import java.util.List;

public class TaskHandler {

    private final Script ctx;

    private final List<AbstractTask> tasks = new ArrayList<>();

    public List<AbstractTask> getTasks() {
        return tasks;
    }

    private AbstractTask active = null;


    public TaskHandler(Script ctx) {
        this.ctx = ctx;
    }

    public void addTask(AbstractTask task) {
        tasks.add(task);
    }

    public void execute() throws InterruptedException {
        for (final AbstractTask task : tasks) {
            if (task.canExecute()) {
                task.execute();
            }
        }
    }

    public void setActive(AbstractTask active) {
        this.active = active;
    }

    public AbstractTask getActive() {
        return active;
    }


}
