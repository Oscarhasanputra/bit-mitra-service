package com.bit.microservices.mitra.configuration;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.DatagramChannel;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.NameResolver;
import io.netty.resolver.RoundRobinInetAddressResolver;
import io.netty.resolver.dns.DnsAddressResolverGroup;
import io.netty.resolver.dns.DnsNameResolverBuilder;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class CustomDnsAddressResolverGroup extends DnsAddressResolverGroup {

    public CustomDnsAddressResolverGroup(DnsNameResolverBuilder dnsResolverBuilder) {
        super(dnsResolverBuilder);
    }

    public CustomDnsAddressResolverGroup(Class<? extends DatagramChannel> channelType,DnsServerAddressStreamProvider nameServerProvider) {
        super(channelType, nameServerProvider);
    }

    public CustomDnsAddressResolverGroup(
            ChannelFactory<? extends DatagramChannel> channelFactory,
            DnsServerAddressStreamProvider nameServerProvider) {
        super(channelFactory, nameServerProvider);
    }

    @Override
    protected final AddressResolver<InetSocketAddress> newAddressResolver(EventLoop eventLoop, NameResolver<InetAddress> resolver)throws Exception {
        return new RoundRobinInetAddressResolver(eventLoop, resolver).asAddressResolver();
    }

}
