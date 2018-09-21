import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICiseRuleSet } from 'app/shared/model/cise-rule-set.model';
import { getEntities as getCiseRuleSets } from 'app/entities/cise-rule-set/cise-rule-set.reducer';
import { ICiseAuthority } from 'app/shared/model/cise-authority.model';
import { getEntities as getCiseAuthorities } from 'app/entities/cise-authority/cise-authority.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cise-service.reducer';
import { ICiseService } from 'app/shared/model/cise-service.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICiseServiceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICiseServiceUpdateState {
  isNew: boolean;
  ciseRuleSetId: string;
  ciseAuthorityId: string;
}

export class CiseServiceUpdate extends React.Component<ICiseServiceUpdateProps, ICiseServiceUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      ciseRuleSetId: '0',
      ciseAuthorityId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCiseRuleSets();
    this.props.getCiseAuthorities();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { ciseServiceEntity } = this.props;
      const entity = {
        ...ciseServiceEntity,
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
    this.props.history.push('/entity/cise-service');
  };

  render() {
    const { ciseServiceEntity, ciseRuleSets, ciseAuthorities, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="gatewayuiApp.ciseService.home.createOrEditLabel">
              <Translate contentKey="gatewayuiApp.ciseService.home.createOrEditLabel">Create or edit a CiseService</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ciseServiceEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="cise-service-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="gatewayuiApp.ciseService.name">Name</Translate>
                  </Label>
                  <AvField
                    id="cise-service-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="serviceTypeLabel">
                    <Translate contentKey="gatewayuiApp.ciseService.serviceType">Service Type</Translate>
                  </Label>
                  <AvInput
                    id="cise-service-serviceType"
                    type="select"
                    className="form-control"
                    name="serviceType"
                    value={(!isNew && ciseServiceEntity.serviceType) || 'VESSEL_DOCUMENT_SERVICE'}
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
                <AvGroup>
                  <Label id="serviceOperationLabel">
                    <Translate contentKey="gatewayuiApp.ciseService.serviceOperation">Service Operation</Translate>
                  </Label>
                  <AvInput
                    id="cise-service-serviceOperation"
                    type="select"
                    className="form-control"
                    name="serviceOperation"
                    value={(!isNew && ciseServiceEntity.serviceOperation) || 'PUSH'}
                  >
                    <option value="PUSH">
                      <Translate contentKey="gatewayuiApp.CiseServiceOperationType.PUSH" />
                    </option>
                    <option value="SUBSCRIBE">
                      <Translate contentKey="gatewayuiApp.CiseServiceOperationType.SUBSCRIBE" />
                    </option>
                    <option value="ACKNOWLEDGEMENT">
                      <Translate contentKey="gatewayuiApp.CiseServiceOperationType.ACKNOWLEDGEMENT" />
                    </option>
                    <option value="FEEDBACK">
                      <Translate contentKey="gatewayuiApp.CiseServiceOperationType.FEEDBACK" />
                    </option>
                    <option value="PULL">
                      <Translate contentKey="gatewayuiApp.CiseServiceOperationType.PULL" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="ciseRuleSet.id">
                    <Translate contentKey="gatewayuiApp.ciseService.ciseRuleSet">Cise Rule Set</Translate>
                  </Label>
                  <AvInput id="cise-service-ciseRuleSet" type="select" className="form-control" name="ciseRuleSetId">
                    <option value="" key="0" />
                    {ciseRuleSets
                      ? ciseRuleSets.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="ciseAuthority.id">
                    <Translate contentKey="gatewayuiApp.ciseService.ciseAuthority">Cise Authority</Translate>
                  </Label>
                  <AvInput id="cise-service-ciseAuthority" type="select" className="form-control" name="ciseAuthorityId">
                    <option value="" key="0" />
                    {ciseAuthorities
                      ? ciseAuthorities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/cise-service" replace color="info">
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
  ciseRuleSets: storeState.ciseRuleSet.entities,
  ciseAuthorities: storeState.ciseAuthority.entities,
  ciseServiceEntity: storeState.ciseService.entity,
  loading: storeState.ciseService.loading,
  updating: storeState.ciseService.updating
});

const mapDispatchToProps = {
  getCiseRuleSets,
  getCiseAuthorities,
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
)(CiseServiceUpdate);
