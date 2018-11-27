package com.chrl.bs.sfs;

import com.chrl.bs.sfs.handler.*;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;

/**
 * 通道类
 *
 * @author
 * @version
 * @since 1.0
 */
public class PipelineFactory implements ChannelPipelineFactory {
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpChunkAggregator(65536));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
        pipeline.addLast("webImages", new PortraitHandler());
        pipeline.addLast("document", new DocumentHandler());
        pipeline.addLast("sampledata", new SampleDataHandler());
        pipeline.addLast("anyFiles", new AnyFileHandler());
        pipeline.addLast("andData", new TextDataHandler());
        pipeline.addLast("reload", new ReloadDataHandler());
        pipeline.addLast("ftpDir", new FtpDirHandler());
        pipeline.addLast("ftpDirFile", new FtpDirFileHandler());
        return pipeline;
    }
}
