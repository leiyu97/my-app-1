package com.larinia.app;

import sun.misc.SharedSecrets;
import sun.misc.VM;

import java.nio.ByteBuffer;

public class DirectMemoryTest {

    final ByteBuffer bytes = ByteBuffer.allocateDirect(1000);
    /**

     * Write amount of direct memory used to standard output

     * using SharedSecrets, JavaNetAccess, the direct Buffer Pool,

     * and methods getMemoryUsed() and getTotalCapacity().

     */

    public static void writeUsedDirectMemoryToStdOut()

    {

        final double sharedSecretsMemoryUsed =

                MemoryUnit.BYTES.toMegaBytes(

                        SharedSecrets.getJavaNioAccess().getDirectBufferPool().getMemoryUsed());

        System.out.println(

                "sun.misc.SharedSecrets.getJavaNioAccess().getDirectBufferPool().getMemoryUsed(): "

                        + sharedSecretsMemoryUsed + " MB");

        final double sharedSecretsTotalCapacity =

                MemoryUnit.BYTES.toMegaBytes(SharedSecrets.getJavaNioAccess().getDirectBufferPool().getTotalCapacity());

        System.out.println("sun.misc.SharedSecrets.getJavaNioAccess().getDirectBufferPool().getTotalCapacity(): "

                + sharedSecretsTotalCapacity + " MB");

    }

    /**

     * Write maximum direct memory size set (explicitly or

     * implicitly) for this VM instance using VM's

     * method maxDirectMemory().

     */

    public static void writeMaximumDirectMemorySizeToStdOut()

    {

        final double vmSize =

                MemoryUnit.BYTES.toMegaBytes(VM.maxDirectMemory());

        System.out.println(

                "sun.misc.VM.maxDirectMemory(): " + vmSize + " MB");

    }

    public static void main(String[] args) {
        DirectMemoryTest.writeMaximumDirectMemorySizeToStdOut();
        DirectMemoryTest.writeUsedDirectMemoryToStdOut();
    }
}
