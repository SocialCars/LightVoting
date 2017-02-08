/**
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of LightVoting by Sophie Dennisen.                               #
 * # Copyright (c) 2017, Sophie Dennisen (sophie.dennisen@tu-clausthal.de)              #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightvoting.simulation.rule;

import java.util.List;

/* TODO later, compute possible committees independently from used voting rule */

/**
 * Created by sophie on 10.01.17.
 */
public class CMinimaxApproval
{

    /* m_alternatives list */
    private List<String> m_alternatives;
    /* list of values*/
    private List<int[]> m_votes;
    /* committee size */
    private int m_comSize;
    /* committee */
    private int[] m_comVect;


    /**
     * compute the winning committee according to Minimax Approval
     *
     * @param p_alternatives available alternatives
     * @param p_votes submitted votes
     * @param p_comSize size of committee to be elected
     * @return elected committee
     *
     */

    public int[] applyRule( final List<String> p_alternatives, final List<int[]> p_votes, final int p_comSize )
    {
        m_alternatives = p_alternatives;
        m_votes = p_votes;
        m_comSize = p_comSize;
        m_comVect = new int[m_alternatives.size()];

        // compute all possible committees, i.e. all {0,1}^m vectors with exactly k ones
        final int[][] l_committees = this.computeComittees( m_votes.size(), m_alternatives.size(), m_comSize );

        return new int[0];
    }

    /**
     * compute all possible committees for given number of alternatives and committee size
     * @param p_votNum number of votes
     * @param p_altNum number of alternatives
     * @param p_comSize size of committee to be elected
     * @return all possible committees
     * TODO use CCombination
     */

    private int[][] computeComittees( final int p_votNum, final int p_altNum, final int p_comSize )
    {
        return new int[0][0];
    }

}
