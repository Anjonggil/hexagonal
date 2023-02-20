package com.example.hexagonal.framework.adapter.out.h2.data;

import com.example.hexagonal.domain.entity.Switch;
import com.example.hexagonal.domain.vo.RouterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "routers")
@SecondaryTable(name = "switches")
@MappedSuperclass
public class RouterData implements Serializable {
    @Id
    @Column(name = "router_id", columnDefinition = "uuid", updatable = false)
    @Convert(converter = UUIDTypeConverter.class)
    private UUID routerId;

    @Embedded
    @Enumerated(EnumType.STRING)
    @Column(name = "router_type")
    private RouterTypeData routerType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(table = "swicthes", name = "router_id", referencedColumnName = "router_id")
    private SwitchData networkSwitch;
}
