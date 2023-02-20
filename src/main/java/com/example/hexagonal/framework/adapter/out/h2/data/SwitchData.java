package com.example.hexagonal.framework.adapter.out.h2.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "switches")
@SecondaryTable(name = "networks")
@MappedSuperclass
public class SwitchData implements Serializable {
    @Id
    @Column(name="switch_id",
            columnDefinition = "uuid",
            updatable = false )
    @Convert(converter = UUIDTypeConverter.class)
    private UUID switchId;

    @Column(name="router_id")
    @Convert(converter = UUIDTypeConverter.class)
    private UUID routerId;

    @Enumerated(EnumType.STRING)
    @Embedded
    @Column(name = "switch_type")
    private SwitchTypeData switchType;

    @OneToMany
    @JoinColumn(table = "networks",
            name = "switch_id",
            referencedColumnName = "switch_id")
    private List<NetworkData> networks;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "address",
                    column = @Column(
                            name = "switch_ip_address")),
            @AttributeOverride(
                    name = "protocol",
                    column = @Column(
                            name = "switch_ip_protocol")),
    })
    private IPData ip;
}
