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

package org.lightvoting.simulation.environment;

import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;
import org.lightvoting.simulation.agent.CChairAgent;
import org.lightvoting.simulation.agent.CVotingAgent;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceArray;


/**
 * Created by sophie on 22.02.17.
 * Environment class
 */
public final class CEnvironment
{
    /**
     * thread-safe structure for group-to-agent mapping
     */
    private final AtomicReferenceArray<CVotingAgent> m_group;

    /**
     * map with agent-to-group-id mapping
     */
  //  private final Map<CVotingAgent, Integer> m_agentgroup = new ConcurrentHashMap<>();


    /**
     * map with chair-to-group mapping
     */

    private final Set<CVotingAgent> m_agents;

    private final Map<CChairAgent, List<CVotingAgent>> m_chairgroup;

    /**
     * maximum size
     */
    private final int m_size;

    /**
     * group capacity
     */

    private final int m_capacity = 3;

    /**
     * Map to indicate if joining a group is allowed
     */

    private final Map<Integer, Boolean> m_joiningAllowed;

    /**
     *constructor
     * @param p_size number of agents
     */
    public CEnvironment( final int p_size )
    {
        m_size = p_size;
        m_group = new AtomicReferenceArray<CVotingAgent>( new CVotingAgent[(int) m_size] );
        m_joiningAllowed = new HashMap<>();
        m_agents = new HashSet<>();
        m_chairgroup = new HashMap<>();
    }

    /**
     * initialize groups
     *
     * @param p_votingAgent agent
     * @return boolean value
     */
    public final void initialset( final CVotingAgent p_votingAgent )
    {
        m_agents.add( p_votingAgent );
    }

    /**
     * open a new group
     * @param p_votingAgent voting opening the group
     */

    public final void openNewGroup( final CVotingAgent p_votingAgent )
    {
        final ITrigger l_triggerChair = CTrigger.from(
            ITrigger.EType.ADDGOAL,
            CLiteral.from(
                "myGroup",
                CLiteral.from( p_votingAgent.name() ) )
        );

        p_votingAgent.getChair().trigger( l_triggerChair );

        final List l_list = new LinkedList<CVotingAgent>();
        l_list.add( p_votingAgent );

        m_chairgroup.put( p_votingAgent.getChair(), l_list );


        final ITrigger l_trigger = CTrigger.from(
            ITrigger.EType.ADDGOAL,
            CLiteral.from(
                "new/group/opened",
                CLiteral.from( p_votingAgent.name() ),
                CLiteral.from( ( p_votingAgent.getChair() ).toString() ) )
        );


           // trigger all agents and tell them that the group was opened

        m_agents
            .parallelStream()
            .forEach( i -> i.trigger( l_trigger ) );

        // TODO this is only for testing, here each agent looks for a group as soon as agent 0 opened its group. Needs to be rewritten for general case
        // if ( m_chairgroup.size() == 3 )
        if ( m_chairgroup.size() == 1 )
        {
            final ITrigger l_triggerJoin = CTrigger.from(
                ITrigger.EType.ADDGOAL,
                CLiteral.from(
                    "lookforgroup" )

            );


            // trigger all agents and tell them to choose one of the available groups

            m_agents
                .parallelStream()
                .forEach( i -> i.trigger( l_triggerJoin ) );
        }
    }

    /**
     * join a group
     * @param p_votingAgent voting agent joining a group
     */

    public final void joinGroup( final CVotingAgent p_votingAgent )
    {

     // TODO Consider what needs to be re-inserted
//<<<<<<< HEAD
//        // we only do something if it is allowed to join the group
//        // TODO later: we need means to ensure that the agent knows that she has to look for another group
//        // TODO doesn't work because of problem with format of group IDs -> reinsert later
//        // if (m_joiningAllowed.get(p_testID))
//        //  {
//        //    System.out.println( "Joining allowed ");
//        final int l_oldSize = m_chairgroup.get( p_votingAgent.getChair() ).size();
//        if ( ( l_oldSize + 1 ) < m_capacity )
//        {
//            // TODO this doesn't work out, you don't need the chair of the joining agent
//            // m_chairgroup.get( p_votingAgent.getChair() ).add( p_votingAgent );
//
//            // TODO There is a discrepancy between p_testID and  m_agentgroup.get( p_votingAgent ) ).toString()!!
//
////            final ITrigger l_triggerChair = CTrigger.from(
////                ITrigger.EType.ADDGOAL,
////                CLiteral.from(
////                    "my/group/new/agent",
////                    CLiteral.from( p_votingAgent.name() ),
////                    CLiteral.from( p_testID.toString()
////                    ))
////
////            );
//
////            p_votingAgent.getChair().trigger( l_triggerChair );
//
//
//            System.out.println( "name of joining agent " + p_votingAgent.name() + " ID: " + String.valueOf( Math.round( p_testID.doubleValue() ) ) );
//            final ITrigger l_trigger = CTrigger.from(
//                ITrigger.EType.ADDGOAL,
//                CLiteral.from(
//                    "joined/group",
//                    CLiteral.from( p_votingAgent.name() ),
//                    CLiteral.from( String.valueOf( (int) ( p_testID.doubleValue() ) ) )
//                )
//            );
//
//
//            // trigger all agents and tell them that the agent joined a group
//            m_agentgroup
//                .keySet()
//                .parallelStream()
//                .forEach( i -> i.trigger( l_trigger ) );
//        }
//
//        // TODO if the capacity is reached, the joining of further agents must be disabled
//        // TODO and the election has to be triggered
//
//        else
//        {
//            m_chairgroup.get( p_votingAgent.getChair() ).add( p_votingAgent );
//
//            final ITrigger l_triggerChair = CTrigger.from(
//                ITrigger.EType.ADDGOAL,
//                CLiteral.from(
//                    "my/group/new/agent",
//                    CLiteral.from( p_votingAgent.name() ),
//                    CLiteral.from( ( m_agentgroup.get( p_votingAgent ) ).toString() )
//                )
//            );
//
//            p_votingAgent.getChair().trigger( l_triggerChair );
//
//            System.out.println( "name of joining agent " + p_votingAgent.name() + " ID: " + String.valueOf( Math.round( p_testID.doubleValue() ) ) );
//            final ITrigger l_trigger = CTrigger.from(
//                ITrigger.EType.ADDGOAL,
//                CLiteral.from(
//                    "joined/group",
//                    CLiteral.from( p_votingAgent.name() ),
//                    CLiteral.from( String.valueOf( (int) ( p_testID.doubleValue() ) ) )
//                )
//            );
//
//
//            // trigger all agents and tell them that the agent joined a group
//            m_agentgroup
//                .keySet()
//                .parallelStream()
//                .forEach( i -> i.trigger( l_trigger ) );
//
//            // tell chair that she needs to start the election
//
//            final ITrigger l_triggerStart = CTrigger.from(
//                ITrigger.EType.ADDGOAL,
//                CLiteral.from(
//                    "start/election" )
//
//            );
//
//            p_votingAgent.getChair().trigger( l_triggerStart );
//
//
//        }
//      //  }
//=======

        // TODO merge with code above

        // choose random group to join

        final List<CChairAgent> l_chairsAsList = new ArrayList<>( m_chairgroup.keySet() );
        final Random l_rand = new Random();

        final CChairAgent l_randomChair = l_chairsAsList.get( l_rand.nextInt( l_chairsAsList.size() ) );
        m_chairgroup.get( l_randomChair ).add( p_votingAgent );

        System.out.println( "name of joining agent " + p_votingAgent.name() );

             //   String l_idString= (p_testID.toString()).replace("[][]","");

                //   System.out.println( "name of joining agent " + p_votingAgent.name() + " ID ohne Annotationen: " + l_id  );

        final ITrigger l_trigger = CTrigger.from(
            ITrigger.EType.ADDGOAL,
            CLiteral.from(
                "joined/group",
                CLiteral.from( p_votingAgent.name() ),
                CLiteral.from( l_randomChair.toString() ) )
            );

        // trigger all agents and tell them that the agent joined a group
        m_agents
            .parallelStream()
            .forEach( i -> i.trigger( l_trigger ) );

    }



    public final int size()
    {
        return m_size;
    }

}
