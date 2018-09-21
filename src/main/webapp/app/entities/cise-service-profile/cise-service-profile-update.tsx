import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICiseRule } from 'app/shared/model/cise-rule.model';
import { getEntities as getCiseRules } from 'app/entities/cise-rule/cise-rule.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cise-service-profile.reducer';
import { ICiseServiceProfile } from 'app/shared/model/cise-service-profile.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICiseServiceProfileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICiseServiceProfileUpdateState {
  isNew: boolean;
  ciseRuleId: string;
}

export class CiseServiceProfileUpdate extends React.Component<ICiseServiceProfileUpdateProps, ICiseServiceProfileUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      ciseRuleId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCiseRules();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { ciseServiceProfileEntity } = this.props;
      const entity = {
        ...ciseServiceProfileEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/cise-service-profile');
  };

  render() {
    const { ciseServiceProfileEntity, ciseRules, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="gatewayuiApp.ciseServiceProfile.home.createOrEditLabel">
              <Translate contentKey="gatewayuiApp.ciseServiceProfile.home.createOrEditLabel">Create or edit a CiseServiceProfile</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ciseServiceProfileEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="cise-service-profile-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.name">Name</Translate>
                  </Label>
                  <AvField
                    id="cise-service-profile-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="serviceIdLabel" for="serviceId">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceId">Service Id</Translate>
                  </Label>
                  <AvField id="cise-service-profile-serviceId" type="text" name="serviceId" />
                </AvGroup>
                <AvGroup>
                  <Label id="communityLabel">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.community">Community</Translate>
                  </Label>
                  <AvInput
                    id="cise-service-profile-community"
                    type="select"
                    className="form-control"
                    name="community"
                    value={(!isNew && ciseServiceProfileEntity.community) || 'FISHERIES_CONTROL'}
                  >
                    <option value="FISHERIES_CONTROL">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.FISHERIES_CONTROL" />
                    </option>
                    <option value="GENERAL_LAW_ENFORCEMENT">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.GENERAL_LAW_ENFORCEMENT" />
                    </option>
                    <option value="CUSTOMS">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.CUSTOMS" />
                    </option>
                    <option value="MARITIME_SAFETY_SECURITY">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.MARITIME_SAFETY_SECURITY" />
                    </option>
                    <option value="NON_SPECIFIED">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.NON_SPECIFIED" />
                    </option>
                    <option value="MARINE_ENVIRONMENT">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.MARINE_ENVIRONMENT" />
                    </option>
                    <option value="OTHER">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.OTHER" />
                    </option>
                    <option value="BORDER_CONTROL">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.BORDER_CONTROL" />
                    </option>
                    <option value="DEFENCE_MONITORING">
                      <Translate contentKey="gatewayuiApp.CiseCommunityType.DEFENCE_MONITORING" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="countryLabel" for="country">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.country">Country</Translate>
                  </Label>
                  <AvField id="cise-service-profile-country" type="text" name="country" />
                </AvGroup>
                <AvGroup>
                  <Label id="dataFreshnessLabel">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.dataFreshness">Data Freshness</Translate>
                  </Label>
                  <AvInput
                    id="cise-service-profile-dataFreshness"
                    type="select"
                    className="form-control"
                    name="dataFreshness"
                    value={(!isNew && ciseServiceProfileEntity.dataFreshness) || 'REAL_TIME'}
                  >
                    <option value="REAL_TIME">
                      <Translate contentKey="gatewayuiApp.CiseDataFreshnessType.REAL_TIME" />
                    </option>
                    <option value="NEARLY_REAL_TIME">
                      <Translate contentKey="gatewayuiApp.CiseDataFreshnessType.NEARLY_REAL_TIME" />
                    </option>
                    <option value="UNKNOWN">
                      <Translate contentKey="gatewayuiApp.CiseDataFreshnessType.UNKNOWN" />
                    </option>
                    <option value="HISTORIC">
                      <Translate contentKey="gatewayuiApp.CiseDataFreshnessType.HISTORIC" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="serviceFunctionLabel">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceFunction">Service Function</Translate>
                  </Label>
                  <AvInput
                    id="cise-service-profile-serviceFunction"
                    type="select"
                    className="form-control"
                    name="serviceFunction"
                    value={(!isNew && ciseServiceProfileEntity.serviceFunction) || 'LAW_ENFORCEMENT_MONITORING'}
                  >
                    <option value="LAW_ENFORCEMENT_MONITORING">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.LAW_ENFORCEMENT_MONITORING" />
                    </option>
                    <option value="NON_SPECIFIED">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.NON_SPECIFIED" />
                    </option>
                    <option value="CUSTOMS_OPERATION">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.CUSTOMS_OPERATION" />
                    </option>
                    <option value="BORDER_OPERATION">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.BORDER_OPERATION" />
                    </option>
                    <option value="BORDER_MONITORING">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.BORDER_MONITORING" />
                    </option>
                    <option value="ENVIRONMENT_MONITORING">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.ENVIRONMENT_MONITORING" />
                    </option>
                    <option value="CUSTOMS_MONITORING">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.CUSTOMS_MONITORING" />
                    </option>
                    <option value="ENVIRONMENT_WARNING">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.ENVIRONMENT_WARNING" />
                    </option>
                    <option value="CSDP_TASK">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.CSDP_TASK" />
                    </option>
                    <option value="COUNTER_TERRORISM">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.COUNTER_TERRORISM" />
                    </option>
                    <option value="LAW_ENFORCEMENT_OPERATION">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.LAW_ENFORCEMENT_OPERATION" />
                    </option>
                    <option value="OPERATION">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.OPERATION" />
                    </option>
                    <option value="FISHERIES_WARNING">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.FISHERIES_WARNING" />
                    </option>
                    <option value="FISHERIES_OPERATION">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.FISHERIES_OPERATION" />
                    </option>
                    <option value="VTM">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.VTM" />
                    </option>
                    <option value="SAFETY">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.SAFETY" />
                    </option>
                    <option value="SECURITY">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.SECURITY" />
                    </option>
                    <option value="FISHERIES_MONITORING">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.FISHERIES_MONITORING" />
                    </option>
                    <option value="DEFENCE_MONITORING">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.DEFENCE_MONITORING" />
                    </option>
                    <option value="SAR">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.SAR" />
                    </option>
                    <option value="ENVIRONMENT_RESPONSE">
                      <Translate contentKey="gatewayuiApp.CiseFunctionType.ENVIRONMENT_RESPONSE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="seaBasinLabel">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.seaBasin">Sea Basin</Translate>
                  </Label>
                  <AvInput
                    id="cise-service-profile-seaBasin"
                    type="select"
                    className="form-control"
                    name="seaBasin"
                    value={(!isNew && ciseServiceProfileEntity.seaBasin) || 'ATLANTIC'}
                  >
                    <option value="ATLANTIC">
                      <Translate contentKey="gatewayuiApp.CiseSeaBasinType.ATLANTIC" />
                    </option>
                    <option value="ARCTIC_OCEAN">
                      <Translate contentKey="gatewayuiApp.CiseSeaBasinType.ARCTIC_OCEAN" />
                    </option>
                    <option value="NON_SPECIFIED">
                      <Translate contentKey="gatewayuiApp.CiseSeaBasinType.NON_SPECIFIED" />
                    </option>
                    <option value="BALTIC_SEA">
                      <Translate contentKey="gatewayuiApp.CiseSeaBasinType.BALTIC_SEA" />
                    </option>
                    <option value="OUTERMOST_REGIONS">
                      <Translate contentKey="gatewayuiApp.CiseSeaBasinType.OUTERMOST_REGIONS" />
                    </option>
                    <option value="NORTH_SEA">
                      <Translate contentKey="gatewayuiApp.CiseSeaBasinType.NORTH_SEA" />
                    </option>
                    <option value="MEDITERRANEAN">
                      <Translate contentKey="gatewayuiApp.CiseSeaBasinType.MEDITERRANEAN" />
                    </option>
                    <option value="BLACK_SEA">
                      <Translate contentKey="gatewayuiApp.CiseSeaBasinType.BLACK_SEA" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="serviceRoleLabel">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceRole">Service Role</Translate>
                  </Label>
                  <AvInput
                    id="cise-service-profile-serviceRole"
                    type="select"
                    className="form-control"
                    name="serviceRole"
                    value={(!isNew && ciseServiceProfileEntity.serviceRole) || 'CONSUMER'}
                  >
                    <option value="CONSUMER">
                      <Translate contentKey="gatewayuiApp.CiseServiceRoleType.CONSUMER" />
                    </option>
                    <option value="PROVIDER">
                      <Translate contentKey="gatewayuiApp.CiseServiceRoleType.PROVIDER" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="serviceTypeLabel">
                    <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceType">Service Type</Translate>
                  </Label>
                  <AvInput
                    id="cise-service-profile-serviceType"
                    type="select"
                    className="form-control"
                    name="serviceType"
                    value={(!isNew && ciseServiceProfileEntity.serviceType) || 'VESSEL_DOCUMENT_SERVICE'}
                  >
                    <option value="VESSEL_DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.VESSEL_DOCUMENT_SERVICE" />
                    </option>
                    <option value="ORGANIZATION_DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.ORGANIZATION_DOCUMENT_SERVICE" />
                    </option>
                    <option value="AIRCRAFT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.AIRCRAFT_SERVICE" />
                    </option>
                    <option value="VESSEL_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.VESSEL_SERVICE" />
                    </option>
                    <option value="DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.DOCUMENT_SERVICE" />
                    </option>
                    <option value="OPERATIONAL_ASSET_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.OPERATIONAL_ASSET_SERVICE" />
                    </option>
                    <option value="MOVEMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.MOVEMENT_SERVICE" />
                    </option>
                    <option value="ACTION_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.ACTION_SERVICE" />
                    </option>
                    <option value="MARITIME_SAFETY_INCIDENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.MARITIME_SAFETY_INCIDENT_SERVICE" />
                    </option>
                    <option value="LAND_VEHICLE_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.LAND_VEHICLE_SERVICE" />
                    </option>
                    <option value="EVENT_DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.EVENT_DOCUMENT_SERVICE" />
                    </option>
                    <option value="RISK_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.RISK_SERVICE" />
                    </option>
                    <option value="LAW_INFRINGEMENT_INCIDENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.LAW_INFRINGEMENT_INCIDENT_SERVICE" />
                    </option>
                    <option value="PERSON_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.PERSON_SERVICE" />
                    </option>
                    <option value="LOCATION_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.LOCATION_SERVICE" />
                    </option>
                    <option value="METEO_OCEANOGRAPHIC_OBSERVATION_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.METEO_OCEANOGRAPHIC_OBSERVATION_SERVICE" />
                    </option>
                    <option value="CERTIFICATE_DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.CERTIFICATE_DOCUMENT_SERVICE" />
                    </option>
                    <option value="AGENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.AGENT_SERVICE" />
                    </option>
                    <option value="INCIDENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.INCIDENT_SERVICE" />
                    </option>
                    <option value="LOCATION_DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.LOCATION_DOCUMENT_SERVICE" />
                    </option>
                    <option value="ANOMALY_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.ANOMALY_SERVICE" />
                    </option>
                    <option value="IRREGULAR_MIGRATION_INCIDENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.IRREGULAR_MIGRATION_INCIDENT_SERVICE" />
                    </option>
                    <option value="CARGO_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.CARGO_SERVICE" />
                    </option>
                    <option value="RISK_DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.RISK_DOCUMENT_SERVICE" />
                    </option>
                    <option value="PERSON_DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.PERSON_DOCUMENT_SERVICE" />
                    </option>
                    <option value="CARGO_DOCUMENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.CARGO_DOCUMENT_SERVICE" />
                    </option>
                    <option value="CRISIS_INCIDENT_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.CRISIS_INCIDENT_SERVICE" />
                    </option>
                    <option value="ORGANIZATION_SERVICE">
                      <Translate contentKey="gatewayuiApp.CiseServiceType.ORGANIZATION_SERVICE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/cise-service-profile" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  ciseRules: storeState.ciseRule.entities,
  ciseServiceProfileEntity: storeState.ciseServiceProfile.entity,
  loading: storeState.ciseServiceProfile.loading,
  updating: storeState.ciseServiceProfile.updating
});

const mapDispatchToProps = {
  getCiseRules,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CiseServiceProfileUpdate);
