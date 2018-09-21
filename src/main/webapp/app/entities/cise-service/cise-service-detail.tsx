import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cise-service.reducer';
import { ICiseService } from 'app/shared/model/cise-service.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICiseServiceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CiseServiceDetail extends React.Component<ICiseServiceDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ciseServiceEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="gatewayuiApp.ciseService.detail.title">CiseService</Translate> [<b>{ciseServiceEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="gatewayuiApp.ciseService.name">Name</Translate>
              </span>
            </dt>
            <dd>{ciseServiceEntity.name}</dd>
            <dt>
              <span id="serviceType">
                <Translate contentKey="gatewayuiApp.ciseService.serviceType">Service Type</Translate>
              </span>
            </dt>
            <dd>{ciseServiceEntity.serviceType}</dd>
            <dt>
              <span id="serviceOperation">
                <Translate contentKey="gatewayuiApp.ciseService.serviceOperation">Service Operation</Translate>
              </span>
            </dt>
            <dd>{ciseServiceEntity.serviceOperation}</dd>
            <dt>
              <Translate contentKey="gatewayuiApp.ciseService.ciseRuleSet">Cise Rule Set</Translate>
            </dt>
            <dd>{ciseServiceEntity.ciseRuleSetId ? ciseServiceEntity.ciseRuleSetId : ''}</dd>
            <dt>
              <Translate contentKey="gatewayuiApp.ciseService.ciseAuthority">Cise Authority</Translate>
            </dt>
            <dd>{ciseServiceEntity.ciseAuthorityId ? ciseServiceEntity.ciseAuthorityId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/cise-service" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/cise-service/${ciseServiceEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ ciseService }: IRootState) => ({
  ciseServiceEntity: ciseService.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CiseServiceDetail);
