/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2023 Mike Millson                                                                               *
 *                                                                                                                    * 
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License       * 
 * v. 2.0 which is available at https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0 which is    *
 * available at https://www.apache.org/licenses/LICENSE-2.0.                                                          *
 *                                                                                                                    *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0                                                                     *
 *                                                                                                                    *
 * Contributors:                                                                                                      *
 *    Mike Millson - initial API and implementation                                                                   *
 *********************************************************************************************************************/
package org.github.krashpad.domain.jdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestCommandLine {

    @Test
    void testEmpty() {
        String logLine = "Command Line:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.COMMAND_LINE,
                JdkUtil.LogEventType.COMMAND_LINE.toString() + " not identified.");
        CommandLine event = new CommandLine(logLine);
        assertNull(event.getValue(), "CommandLine value not correct.");
    }

    @Test
    void testIdentity() {
        String logLine = "Command Line: -Xmx2048m -Xmx12G -Xms1G";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.COMMAND_LINE,
                JdkUtil.LogEventType.COMMAND_LINE.toString() + " not identified.");
    }

    @Test
    void testJvmOptions() {
        String jvmArgs = "--add-opens=java.base/java.lang=ALL-UNNAMED "
                + "--add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED "
                + "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED "
                + "--add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED "
                + "-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager "
                + "-Djavax.net.ssl.trustStore=/opt/keystores/truststore -DJWS_LOG_HOME=/home/tomcat/MYAPP "
                + "-Dorg.apache.catalina.connector.RECYCLE_FACADES=true "
                + "-Dorg.apache.catalina.connector.CoyoteAdapter.ALLOW_BACKSLASH=false "
                + "-Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=false "
                + "-Dorg.apache.catalina.connector.Response.ENFORCE_ENCODING_IN_GET_WRITER=true "
                + "-Djava.library.path=/home/tomcat/lib -Djdk.tls.ephemeralDHKeySize=2048 "
                + "-Djava.protocol.handler.pkgs=org.apache.catalina.webresources "
                + "-Dorg.apache.catalina.security.SecurityListener.UMASK=0026 -XX:+UseG1GC -Xms11264m -Xmx11264m "
                + "-XX:MaxGCPauseMillis=300 -XX:+DisableExplicitGC -XX:InitiatingHeapOccupancyPercent=75 "
                + "-XX:MaxTenuringThreshold=10 -XX:+UseStringDeduplication -XX:+ParallelRefProcEnabled "
                + "-XX:MaxMetaspaceSize=384m "
                + "-Xlog:gc=debug:file=/home/tomcat/MYAPP/logs/gc.log:time,uptime,level,tags:filecount=5,filesize=50m "
                + "-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/dev/null -Dappdynamics.http.proxyHost=myproxy "
                + "-Dappdynamics.http.proxyPort=12345 -Dappdynamics.controller.hostName=myhost "
                + "-Dappdynamics.controller.port=443 -Dappdynamics.controller.ssl.enabled=true "
                + "-Dappdynamics.agent.accountName=myacct -Dappdynamics.agent.accountAccessKey=mykey "
                + "-Dappdynamics.agent.applicationName=myapp -Dappdynamics.agent.tierName=mytier "
                + "-Dappdynamics.agent.nodeName=mynode -Dappdynamics.agent.uniqueHostId=myhostid "
                + "-Dappdynamics.force.default.ssl.certificate.validation=false "
                + "-javaagent:/home/tomcat/appdynamics/java-agent/javaagent.jar -Dignore.endorsed.dirs= "
                + "-Dcatalina.base=/home/tomcat/MYAPP -Dcatalina.home=/home/tomcat "
                + "-Djava.io.tmpdir=/home/tomcat/MYAPP/temp";
        String javaCommand = "org.apache.catalina.startup.Bootstrap start";
        String logLine = "Command Line: " + jvmArgs + " " + javaCommand;
        CommandLine event = new CommandLine(logLine);
        assertEquals(jvmArgs, event.getJvmOptions(), "JVM options not correct.");
    }

    @Test
    void testJvmOptionsNoJavaCommand() {
        String jvmArgs = "-Xmx2048m -Xmx12G -Xms1G";
        String javaCommand = "";
        String logLine = "Command Line: " + jvmArgs + " " + javaCommand;
        CommandLine event = new CommandLine(logLine);
        assertEquals(jvmArgs, event.getJvmOptions(), "JVM options not correct.");
    }

    @Test
    void testJvmOptionsNone() {
        String jvmArgs = null;
        String javaCommand = "TestCrash";
        String logLine = "Command Line: " + jvmArgs + " " + javaCommand;
        CommandLine event = new CommandLine(logLine);
        assertNull(event.getJvmOptions(), "JVM options not correct.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Command Line: -Xmx2048m -Xmx12G -Xms1G";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CommandLine,
                JdkUtil.LogEventType.COMMAND_LINE.toString() + " not parsed.");
    }

    @Test
    void testValue() {
        String logLine = "Command Line: -Xmx2048m -Xmx12G -Xms1G";
        CommandLine event = new CommandLine(logLine);
        assertEquals("-Xmx2048m -Xmx12G -Xms1G", event.getValue(), "CommandLine value not correct.");
    }
}