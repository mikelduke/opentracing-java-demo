package com.mikelduke.vhs.members;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.jaegertracing.LogData;
import io.jaegertracing.reporters.InMemoryReporter;
import io.jaegertracing.reporters.Reporter;
import io.jaegertracing.samplers.ConstSampler;
import io.jaegertracing.samplers.Sampler;
import io.opentracing.Tracer;

@SpringBootApplication
public class TracerConfig {

	@Bean
	public Tracer getTracer() {
        //TODO Use real reported to istio tracing
		Reporter reporter = new InMemoryReporter() {
            @Override
            public void report(io.jaegertracing.Span span) {
                super.report(span);
                String logs = "";

                if (span != null) {
                    if (span.getLogs() != null) {
                        for (LogData l : span.getLogs()) {
                            logs += "\n\t" + l.getTime() + ": " + l.getMessage();
                        };
                    }

                    System.out.println("Span Reported: " + span 
                            + " Tags: " + span.getTags() + " \nLogs: " + logs);
                } else {
                    System.out.println("Span was null");
                }
            }
        };
        
        Sampler sampler = new ConstSampler(true);
        io.opentracing.Tracer tracer = new io.jaegertracing.Tracer.Builder("videostore-members")
                .withReporter(reporter)
                .withSampler(sampler)
                .build();
        
        return tracer;
	}
}