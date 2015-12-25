package com.stress.stressMonks;

import omniapi.OmniScript;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.util.ExperienceTracker;

import java.awt.*;

/**
 * Copyright (c) 2015  Stress
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Created by Stress on 12/23/2015.
 * This file is part of com.stress.data.
 * Created with IntelliJ IDEA.
 *
 * @info    The main class for stressMonks.
 */
public class StressMonks extends OmniScript {

    ExperienceTracker xpTracc;

    @Override
    public void onStart() {
      xpTracc.startAll();

    }

    @Override
    public int onLoop() throws InterruptedException {
        return 0;
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.drawString("atk xp: " + getExperienceTracker().getGainedXP(Skill.STRENGTH), 140, 250);

    }
}
