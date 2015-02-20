/*
 * Copyright (c) 2014 Adam Howard <aeh AT aehdev DOT net>.
 *
 * This file is part of Random Sentinels.
 *
 * Random Sentinels is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Random Sentinels is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Random Sentinels. If not, see <http://www.gnu.org/licenses/>.
 */

package net.aehdev.randomsentinels.model;

public class Hero extends GameElement {

    private int complexity;

    /**
     * Constructor to set all fields.
     *
     * @param id         Unique ID for the hero represented by this object
     * @param name       Name of the hero
     * @param complexity A number from 1 to 3 representing how hard it is to play the hero
     * @param expansion  The expansion set in which the hero's deck is found
     */
    public Hero(long id, String name, int complexity, String expansion) {
        super(id, name, expansion);
        this.complexity = complexity;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }
}
