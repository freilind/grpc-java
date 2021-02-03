package com.grpc.greeting.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import com.proto.sum.Sum;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SumClient {

    public static void main(String[] args) {
        System.out.println("Hello i'm a gRPC client sum");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();

        System.out.println("Creating stub");
        // DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);
        // DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel);

        // created a greet service client (blocking - synchronous )
        SumServiceGrpc.SumServiceBlockingStub sumClient = SumServiceGrpc.newBlockingStub(channel);

        Sum sum = Sum.newBuilder()
                .setFirstNum(3)
                .setSecondNum(10)
                .build();

        SumRequest sumRequest = SumRequest.newBuilder()
                .setSum(sum)
                .build();

        // call the RPC and get back a GreetResponse (protocol buffers)
        SumResponse sumResponse = sumClient.sum(sumRequest);

        System.out.println(sumResponse.getResult());

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
