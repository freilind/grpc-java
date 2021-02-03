package com.grpc.greeting.server;

import com.proto.greet.GreetResponse;
import com.proto.greet.Greeting;
import com.proto.sum.Sum;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SumServiceImpl extends SumServiceGrpc.SumServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        Sum sum = request.getSum();
        int firstNum = sum.getFirstNum();
        int secondNum = sum.getSecondNum();

        //create the response
        int result = firstNum + secondNum;
        SumResponse response = SumResponse.newBuilder()
                .setResult(result)
                .build();

        //send the response
        responseObserver.onNext(response);

        //complete the RPC call
        responseObserver.onCompleted();
    }
}
