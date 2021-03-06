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


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.lightjason.agentspeak.configuration.CDefaultAgentConfiguration;
import org.lightvoting.simulation.environment.CEnvironment;


/**
 * Unit test for CVotingAgent.
 */
public final class CVotingAgentTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param p_testName name of the test case
     */
    public CVotingAgentTest( final String p_testName )
    {
        super( p_testName );
    }

    /**
     * Testsuite
     *
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CVotingAgentTest.class );
    }

    /* TODO fix test */

    /**
     * Testing CVotingAgent Class
     *
     *
     */
    public void testCVotingAgent()
    {
        try
        {
            final CChairAgent l_chairAgent = new CChairAgent( "chair", new CDefaultAgentConfiguration<>(), new CEnvironment( 23, "foo.h5" ),
                                                              "RANDOM",
                                                              "BASIC",
                                                              "foo.h5"
            );
            final CVotingAgent l_agent = new CVotingAgent( "agent", new CDefaultAgentConfiguration<>(), l_chairAgent, new CEnvironment( 23, "foo.h5" ), 10,
                                                           "RANDOM",
                                                           "foo.h5"
            );
            l_agent.call();
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
        }
    }
}

