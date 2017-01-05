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

package org.lightvoting.simulation.agent;

import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightvoting.simulation.action.CMyId;

import java.util.stream.Stream;
import java.io.InputStream;
import java.util.stream.Collectors;

/**
 * Code from https://lightjason.github.io/tutorial/tutorial-agentspeak-in-fifteen-minutes/
 */
public final class CVotingAgentGenerator extends IBaseAgentGenerator<CVotingAgent>
{

    /**
     * constructor of the generator
     * @param p_stream ASL code as any stream e.g. FileInputStream
     * @throws Exception Thrown if something goes wrong while generating agents.
     */
    public CVotingAgentGenerator( final InputStream p_stream ) throws Exception
    {
        super(
                // input ASL stream
                p_stream,

                // a set with all possible actions for the agent
                Stream.concat(
                        // we use all build-in actions of LightJason
                        CCommon.actionsFromPackage(),
                        Stream.concat(
                                // use the actions which are defined inside the agent class
                                CCommon.actionsFromAgentClass( CVotingAgent.class ),
                                // add an own external action
                                Stream.of(
                                        new CMyId()
                                )
                        )
                        // build the set with a collector
                ).collect( Collectors.toSet() ),

                // aggregation function for the optimization function, here
                // we use an empty function
                IAggregation.EMPTY
        );
    }

    // generator method of the agent
    // @param p_data any data which can be put from outside to the generator method
    // @return returns an agent
    @Override
    public final CVotingAgent generatesingle( final Object... p_data )
    {
        return new CVotingAgent( m_configuration );
    }
}