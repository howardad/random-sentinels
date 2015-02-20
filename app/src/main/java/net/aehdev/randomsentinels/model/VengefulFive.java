/*
 * Copyright (c) 2015 Adam Howard <aeh AT aehdev DOT net>.
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class VengefulFive extends Villain {

    public static final long ID = 19;

    public static final Villain BARON_BLADE = new Villain(191l, "Baron Blade", 3, "vengeance");
    public static final Villain ERMINE = new Villain(192l, "Ermine", 2, "vengeance");
    public static final Villain FRICTION = new Villain(193l, "Friction", 2, "vengeance");
    public static final Villain FRIGHT_TRAIN = new Villain(194l, "Fright Train", 3, "vengeance");
    public static final Villain PROLETARIAT = new Villain(195l, "Proletariat", 2, "vengeance");
    public static final List<Villain> VENGEFUL_FIVE =
            Arrays.asList(BARON_BLADE, ERMINE, FRICTION, FRIGHT_TRAIN, PROLETARIAT);

    private Set<Villain> vengefulOnes;

    public VengefulFive(int numHeroes) {
        super(ID, "Vengeful Five", 3, "vengeance");
        Random random = new Random();
        vengefulOnes = new HashSet<>(numHeroes * 2);
        while (vengefulOnes.size() < numHeroes) {
            vengefulOnes.add(VENGEFUL_FIVE.get(random.nextInt(numHeroes)));
        }
    }

    public Set<Villain> getVengefulOnes() {
        return vengefulOnes;
    }
}
