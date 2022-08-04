#!/bin/bash

echo "########### Creating SQS ###########"
aws sqs create-queue --endpoint-url=http://localhost:4566 --queue-name=CriarLancamentoFluxoCaixaFila --profile=localstack
aws sqs create-queue --endpoint-url=http://localhost:4566 --queue-name=RespostaCriacaoLancamentoFluxoCaixaFila --profile=localstack
aws sqs create-queue --endpoint-url=http://localhost:4566 --queue-name=CriarPagarReceber --profile=localstack
aws sqs create-queue --endpoint-url=http://localhost:4566 --queue-name=RespostaCriacaoPagarReceberVenda --profile=localstack

echo "########### Creating SNS ###########"
aws sns create-topic --endpoint-url=http://localhost:4566 --name RespostaCriacaoPagarReceber --profile=localstack
SUBSCRIBE_ARN=$(aws sns subscribe --endpoint-url=http://localhost:4566 --topic-arn arn:aws:sns:us-east-1:000000000000:RespostaCriacaoPagarReceber --protocol sqs --notification-endpoint http://localstack:4566/000000000000/RespostaCriacaoPagarReceberVenda --output text --profile=localstack)
aws sns set-subscription-attributes --endpoint-url=http://localhost:4566 --subscription-arn=$SUBSCRIBE_ARN --attribute-name=RawMessageDelivery --attribute-value=true --profile=localstack
