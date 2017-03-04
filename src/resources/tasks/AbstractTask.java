/*
*
* Created May 10, 2016, 2:17:44 PM. 
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

public abstract class AbstractTask {

    public static String status, mode;
    protected final Script ctx;
    private AbstractTask task;

    public AbstractTask(Script ctx) {
        this.ctx = ctx;

    }

    public abstract boolean canExecute();

    public abstract void execute() throws InterruptedException;

    public AbstractTask getTask() {
        return task;
    }


}
