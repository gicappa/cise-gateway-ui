import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICiseServiceProfile } from 'app/shared/model/cise-service-profile.model';
import { getEntities as getCiseServiceProfiles } from 'app/entities/cise-service-profile/cise-service-profile.reducer';
import { ICiseRuleSet } from 'app/shared/model/cise-rule-set.model';
import { getEntities as getCiseRuleSets } from 'app/entities/cise-rule-set/cise-rule-set.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cise-rule.reducer';
import { ICiseRule } from 'app/shared/model/cise-rule.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICiseRuleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICiseRuleUpdateState {
  isNew: boolean;
  ciseServiceProfileId: string;
  ciseRuleSetId: string;
}

export class CiseRuleUpdate extends React.Component<ICiseRuleUpdateProps, ICiseRuleUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      ciseServiceProfileId: '0',
      ciseRuleSetId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCiseServiceProfiles();
    this.props.getCiseRuleSets();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { ciseRuleEntity } = this.props;
      const entity = {
        ...ciseRuleEntity,
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
    this.props.history.push('/entity/cise-rule');
  };

  render() {
    const { ciseRuleEntity, ciseServiceProfiles, ciseRuleSets, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="gatewayuiApp.ciseRule.home.createOrEditLabel">
              <Translate contentKey="gatewayuiApp.ciseRule.home.createOrEditLabel">Create or edit a CiseRule</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ciseRuleEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="cise-rule-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="gatewayuiApp.ciseRule.name">Name</Translate>
                  </Label>
                  <AvField
                    id="cise-rule-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="ruleTypeLabel">
                    <Translate contentKey="gatewayuiApp.ciseRule.ruleType">Rule Type</Translate>
                  </Label>
                  <AvInput
                    id="cise-rule-ruleType"
                    type="select"
                    className="form-control"
                    name="ruleType"
                    value={(!isNew && ciseRuleEntity.ruleType) || 'ALLOW_ALL_FILEDS'}
                  >
                    <option value="ALLOW_ALL_FILEDS">
                      <Translate contentKey="gatewayuiApp.CiseRuleType.ALLOW_ALL_FILEDS" />
                    </option>
                    <option value="ALLOW_SPECIFIC_FILEDS">
                      <Translate contentKey="gatewayuiApp.CiseRuleType.ALLOW_SPECIFIC_FILEDS" />
                    </option>
                    <option value="DENY_ALL_FIELDS">
                      <Translate contentKey="gatewayuiApp.CiseRuleType.DENY_ALL_FIELDS" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="entityTemplateLabel" for="entityTemplate">
                    <Translate contentKey="gatewayuiApp.ciseRule.entityTemplate">Entity Template</Translate>
                  </Label>
                  <AvField
                    id="cise-rule-entityTemplate"
                    type="text"
                    name="entityTemplate"
                    validate={{
                      maxLength: { value: 4096, errorMessage: translate('entity.validation.maxlength', { max: 4096 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="ciseServiceProfile.id">
                    <Translate contentKey="gatewayuiApp.ciseRule.ciseServiceProfile">Cise Service Profile</Translate>
                  </Label>
                  <AvInput id="cise-rule-ciseServiceProfile" type="select" className="form-control" name="ciseServiceProfileId">
                    <option value="" key="0" />
                    {ciseServiceProfiles
                      ? ciseServiceProfiles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="ciseRuleSet.id">
                    <Translate contentKey="gatewayuiApp.ciseRule.ciseRuleSet">Cise Rule Set</Translate>
                  </Label>
                  <AvInput id="cise-rule-ciseRuleSet" type="select" className="form-control" name="ciseRuleSetId">
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
                <Button tag={Link} id="cancel-save" to="/entity/cise-rule" replace color="info">
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
  ciseServiceProfiles: storeState.ciseServiceProfile.entities,
  ciseRuleSets: storeState.ciseRuleSet.entities,
  ciseRuleEntity: storeState.ciseRule.entity,
  loading: storeState.ciseRule.loading,
  updating: storeState.ciseRule.updating
});

const mapDispatchToProps = {
  getCiseServiceProfiles,
  getCiseRuleSets,
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
)(CiseRuleUpdate);
