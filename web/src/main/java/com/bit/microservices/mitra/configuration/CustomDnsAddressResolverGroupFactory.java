package com.bit.microservices.mitra.configuration;

import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.resolver.ResolvedAddressTypes;
import io.netty.resolver.dns.*;
import org.redisson.connection.AddressResolverGroupFactory;

public class CustomDnsAddressResolverGroupFactory implements AddressResolverGroupFactory {

    @Override
    public DnsAddressResolverGroup create(Class<? extends DatagramChannel> channelType,
                                          Class<? extends SocketChannel> socketChannelType,
                                          DnsServerAddressStreamProvider nameServerProvider) {
        DnsNameResolverBuilder dnsResolverBuilder = new DnsNameResolverBuilder();
        dnsResolverBuilder.queryTimeoutMillis(150000)
        .ttl(0, Integer.MAX_VALUE)
        .resolvedAddressTypes(ResolvedAddressTypes.IPV4_ONLY)
        .channelType(NioDatagramChannel.class)
        .nameServerProvider(DnsServerAddressStreamProviders.platformDefault())
                .socketChannelType(socketChannelType)
                .resolveCache(new DefaultDnsCache())
                .cnameCache(new DefaultDnsCnameCache());

        return new CustomDnsAddressResolverGroup(dnsResolverBuilder);
    }

}
