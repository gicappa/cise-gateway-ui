import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cise-service-profile.reducer';
import { ICiseServiceProfile } from 'app/shared/model/cise-service-profile.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICiseServiceProfileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CiseServiceProfileDetail extends React.Component<ICiseServiceProfileDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ciseServiceProfileEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="gatewayuiApp.ciseServiceProfile.detail.title">CiseServiceProfile</Translate> [
            <b>{ciseServiceProfileEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.name">Name</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.name}</dd>
            <dt>
              <span id="serviceId">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceId">Service Id</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.serviceId}</dd>
            <dt>
              <span id="community">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.community">Community</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.community}</dd>
            <dt>
              <span id="country">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.country">Country</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.country}</dd>
            <dt>
              <span id="dataFreshness">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.dataFreshness">Data Freshness</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.dataFreshness}</dd>
            <dt>
              <span id="serviceFunction">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceFunction">Service Function</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.serviceFunction}</dd>
            <dt>
              <span id="seaBasin">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.seaBasin">Sea Basin</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.seaBasin}</dd>
            <dt>
              <span id="serviceRole">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceRole">Service Role</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.serviceRole}</dd>
            <dt>
              <span id="serviceType">
                <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceType">Service Type</Translate>
              </span>
            </dt>
            <dd>{ciseServiceProfileEntity.serviceType}</dd>
          </dl>
          <Button tag={Link} to="/entity/cise-service-profile" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/cise-service-profile/${ciseServiceProfileEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ciseServiceProfile }: IRootState) => ({
  ciseServiceProfileEntity: ciseServiceProfile.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CiseServiceProfileDetail);
