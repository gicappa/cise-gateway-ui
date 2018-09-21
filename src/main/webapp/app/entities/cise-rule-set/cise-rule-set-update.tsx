import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICiseService } from 'app/shared/model/cise-service.model';
import { getEntities as getCiseServices } from 'app/entities/cise-service/cise-service.reducer';
import { ICiseAuthority } from 'app/shared/model/cise-authority.model';
import { getEntities as getCiseAuthorities } from 'app/entities/cise-authority/cise-authority.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cise-rule-set.reducer';
import { ICiseRuleSet } from 'app/shared/model/cise-rule-set.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICiseRuleSetUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICiseRuleSetUpdateState {
  isNew: boolean;
  ciseServiceId: string;
  ciseAuthorityId: string;
}

export class CiseRuleSetUpdate extends React.Component<ICiseRuleSetUpdateProps, ICiseRuleSetUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      ciseServiceId: '0',
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

    this.props.getCiseServices();
    this.props.getCiseAuthorities();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { ciseRuleSetEntity } = this.props;
      const entity = {
        ...ciseRuleSetEntity,
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
    this.props.history.push('/entity/cise-rule-set');
  };

  render() {
    const { ciseRuleSetEntity, ciseServices, ciseAuthorities, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="gatewayuiApp.ciseRuleSet.home.createOrEditLabel">
              <Translate contentKey="gatewayuiApp.ciseRuleSet.home.createOrEditLabel">Create or edit a CiseRuleSet</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ciseRuleSetEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="cise-rule-set-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="gatewayuiApp.ciseRuleSet.name">Name</Translate>
                  </Label>
                  <AvField
                    id="cise-rule-set-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="gatewayuiApp.ciseRuleSet.description">Description</Translate>
                  </Label>
                  <AvField
                    id="cise-rule-set-description"
                    type="text"
                    name="description"
                    validate={{
                      maxLength: { value: 4096, errorMessage: translate('entity.validation.maxlength', { max: 4096 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="ciseAuthority.id">
                    <Translate contentKey="gatewayuiApp.ciseRuleSet.ciseAuthority">Cise Authority</Translate>
                  </Label>
                  <AvInput id="cise-rule-set-ciseAuthority" type="select" className="form-control" name="ciseAuthorityId">
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
                <Button tag={Link} id="cancel-save" to="/entity/cise-rule-set" replace color="info">
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
  ciseServices: storeState.ciseService.entities,
  ciseAuthorities: storeState.ciseAuthority.entities,
  ciseRuleSetEntity: storeState.ciseRuleSet.entity,
  loading: storeState.ciseRuleSet.loading,
  updating: storeState.ciseRuleSet.updating
});

const mapDispatchToProps = {
  getCiseServices,
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
)(CiseRuleSetUpdate);
