import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cise-authority.reducer';
import { ICiseAuthority } from 'app/shared/model/cise-authority.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICiseAuthorityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CiseAuthorityDetail extends React.Component<ICiseAuthorityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ciseAuthorityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="gatewayuiApp.ciseAuthority.detail.title">CiseAuthority</Translate> [<b>{ciseAuthorityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="gatewayuiApp.ciseAuthority.name">Name</Translate>
              </span>
            </dt>
            <dd>{ciseAuthorityEntity.name}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="gatewayuiApp.ciseAuthority.description">Description</Translate>
              </span>
            </dt>
            <dd>{ciseAuthorityEntity.description}</dd>
            <dt>
              <Translate contentKey="gatewayuiApp.ciseAuthority.user">User</Translate>
            </dt>
            <dd>{ciseAuthorityEntity.userId ? ciseAuthorityEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/cise-authority" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/cise-authority/${ciseAuthorityEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ciseAuthority }: IRootState) => ({
  ciseAuthorityEntity: ciseAuthority.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CiseAuthorityDetail);
