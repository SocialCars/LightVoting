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

package org.lightvoting.simulation.action;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightvoting.simulation.agent.CVotingAgent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Unit test for CMyId.
 */
public final class CMyIdTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param p_testName name of the test case
     */
    public CMyIdTest( final String p_testName )
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
        return new TestSuite( CMyIdTest.class );
    }

    /**
     * Testing MyId Class
     */
    public void testMyId()
    {
        // check for correct name and number of arguments
        final CMyId l_myid = new CMyId();
        assertEquals( CPath.from( "my/id" ), l_myid.name() );
        assertEquals( 0, l_myid.minimalArgumentNumber() );

        // test execution of my/id
        final ByteArrayOutputStream l_outContent = new ByteArrayOutputStream();
        System.setOut( new PrintStream( l_outContent ) );

        try
        {
            final InputStream l_aslstream = new ByteArrayInputStream(
                    "!main.\n+!main <- my/id.".getBytes( "UTF-8" )
            );

            final CVotingAgent l_agent = new CMyIdAgentGenerator( l_aslstream ).generatesingle();

            l_agent.call();

            // check for correct printout
            assertTrue(
                    l_outContent.toString().contains(
                            "standalone action is called from agent"
                    )
            );
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            // fail test if exception occurred
            assertTrue( false );
            return;
        }

        System.setOut( null );
    }

    private final class CMyIdAgentGenerator extends IBaseAgentGenerator<CVotingAgent>
    {

        public CMyIdAgentGenerator( final InputStream p_stream ) throws Exception
        {
            super(
                    p_stream,
                    Stream.concat(
                            CCommon.actionsFromPackage(),
                            Stream.concat(
                                    CCommon.actionsFromAgentClass( CVotingAgent.class ),
                                    Stream.of(
                                            new CMyId()
                                    )
                            )
                    ).collect( Collectors.toSet() ),
                    IAggregation.EMPTY
            );
        }

        @Override
        public final CVotingAgent generatesingle( final Object... p_data )
        {
            return new CVotingAgent( m_configuration );
        }
    }
}
