/*
 * @(#)UnitCostCenterAutoCompleteProvider.java
 *
 * Copyright 2011 Instituto Superior Tecnico
 * Founding Authors: Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Internal Mobility Module.
 *
 *   The Internal Mobility Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Internal Mobility  Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Internal Mobility  Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.mobility.presentationTier.renderers.dataProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import org.fenixedu.commons.StringNormalizer;

import module.mobility.domain.MobilitySystem;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Party;
import module.organization.domain.Unit;

/**
 * 
 * @author Susana Fernandes
 * 
 */
public class UnitCostCenterAutoCompleteProvider implements AutoCompleteProvider {

    @Override
    public Collection getSearchResults(Map map, String value, int maxCount) {
        final List<Unit> units = new ArrayList<Unit>();

        final String trimmedValue = value.trim();
        final String[] input = StringNormalizer.normalize(trimmedValue).split(" ");

        for (final Party party : getParties((Map<String, String>) map, value)) {
            if (party.isUnit() && party.getPartyTypes().contains(MobilitySystem.getInstance().getCostCenterPartyType())) {
                final Unit unit = (Unit) party;
                if (isActive(unit)) {
                    final String unitName = StringNormalizer.normalize(unit.getPartyName().getContent());
                    if (hasMatch(input, unitName)) {
                        units.add(unit);
                    } else {
                        final String unitAcronym = StringNormalizer.normalize(unit.getAcronym());
                        if (hasMatch(input, unitAcronym)) {
                            units.add(unit);
                        }
                    }
                }
            }
        }

        Collections.sort(units, Unit.COMPARATOR_BY_PRESENTATION_NAME);

        return units;
    }

    private boolean isActive(Unit unit) {
        final AccountabilityType type = MobilitySystem.getInstance().getOrganizationalAccountabilityType();
        return unit.getParentAccountabilityStream().anyMatch(a -> a.getAccountabilityType() == type && a.isActiveNow());
    }

    private boolean hasMatch(final String[] input, final String unitNameParts) {
        for (final String namePart : input) {
            if (unitNameParts.indexOf(namePart) == -1) {
                return false;
            }
        }
        return true;
    }

    protected Set<Party> getParties(Map<String, String> argsMap, String value) {
        return Bennu.getInstance().getPartiesSet();
    }

}
