import React from 'react';
import { Spinner } from 'react-mdl';

require('./loading-screen.scss');
const imgUrl = require(
  '../../../../resources/images/planner.png'
);

function LoadingScreen() {
  return (
    <div className="loading-container">
      <img role="presentation" alt="Planner  logo" src={imgUrl} />
      <Spinner singleColor />
    </div>
  );
}

export default LoadingScreen;
