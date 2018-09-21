import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cise-rule-set.reducer';
import { ICiseRuleSet } from 'app/shared/model/cise-rule-set.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICiseRuleSetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CiseRuleSetDetail extends React.Component<ICiseRuleSetDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ciseRuleSetEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="gatewayuiApp.ciseRuleSet.detail.title">CiseRuleSet</Translate> [<b>{ciseRuleSetEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="gatewayuiApp.ciseRuleSet.name">Name</Translate>
              </span>
            </dt>
            <dd>{ciseRuleSetEntity.name}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="gatewayuiApp.ciseRuleSet.description">Description</Translate>
              </span>
            </dt>
            <dd>{ciseRuleSetEntity.description}</dd>
            <dt>
              <Translate contentKey="gatewayuiApp.ciseRuleSet.ciseAuthority">Cise Authority</Translate>
            </dt>
            <dd>{ciseRuleSetEntity.ciseAuthorityId ? ciseRuleSetEntity.ciseAuthorityId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/cise-rule-set" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/cise-rule-set/${ciseRuleSetEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ciseRuleSet }: IRootState) => ({
  ciseRuleSetEntity: ciseRuleSet.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CiseRuleSetDetail);
